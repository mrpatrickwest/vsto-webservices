package edu.rpi.tw.vsto.repositories;

import edu.rpi.tw.vsto.model.VstoClassType;
import edu.rpi.tw.vsto.model.VstoClassTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional
public final class ClassTypeRepository implements IClassTypeRepository {

    public final Logger log = LoggerFactory.getLogger(ClassTypeRepository.class);

	private Map<Integer, VstoClassType> classTypeMap = null;

	private static final VstoClassTypeMapper CLASSTYPE_MAPPER = new VstoClassTypeMapper();

	private static final StringBuffer GET_TYPES = new StringBuffer()
			.append("select * from tbl_class_type type");

	@Autowired
	INoteRepository noteRepository;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * @param id
	 * @return
	 */
	public VstoClassType findClassType(int id) {
		VstoClassType type = null;
        getClassTypes(false);
		if(classTypeMap != null) {
			type = classTypeMap.get(id);
		}
		return type;
    }

	/**
	 * @param classType
	 * @return
	 */
	public List<VstoClassType> findChildren(VstoClassType classType) {
        List<VstoClassType> children = new ArrayList<>();
		List<VstoClassType> types = getClassTypes(false);
		if(types != null) {
			for(VstoClassType type : types) {
				if(type.getParentId() == classType.getId()) {
					children.add(type);
				}
			}
		}
        return children;
    }

	/**
	 * @param refresh
	 * @return
	 */
	public List<VstoClassType> getClassTypes(boolean refresh) {
		final Map<String, Object> params = new HashMap<>();

		List<VstoClassType> types = null;

		if(refresh || classTypeMap == null) {
			try {
				types = this.jdbcTemplate.query(GET_TYPES.toString(), params, CLASSTYPE_MAPPER);
			} catch (final EmptyResultDataAccessException erdae) {
				log.error("Failed to retrieve the class types " + erdae.getMessage());
				//NOOP
			}
		}

		if(types != null && types.size() > 0) {
			if(refresh || classTypeMap == null) {
				classTypeMap = null;
				for(VstoClassType type : types) {
					if(classTypeMap == null) classTypeMap = new TreeMap<>();
					classTypeMap.put(type.getId(), type);
				}
                for(VstoClassType type : types) {
                    loadExternals(type);
                }
			}
        } else if(classTypeMap != null) {
            types = new ArrayList<>();
            for(VstoClassType type : classTypeMap.values()) {
                types.add(type);
            }
        }

		return types;
    }

    private void loadExternals(VstoClassType classType) {
		if(classType.getParentId() != 0) classType.setParent(this.findClassType(classType.getParentId()));
		if(classType.getNoteId() != 0) classType.setNote(noteRepository.findNote(classType.getNoteId()));
	}

	/**
	 * @return
	 */
	public long totalClassTypes() {
		long num = 0;
        List<VstoClassType> types = getClassTypes(false);
		if(types != null) num = types.size();
		return num;
    }

	/**
	 * @return
	 */
	public void refreshClassTypes() {
        getClassTypes(true);
    }
}

