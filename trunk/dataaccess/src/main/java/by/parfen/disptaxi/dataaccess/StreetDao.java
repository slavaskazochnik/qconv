package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.datamodel.UserProfile;

public interface StreetDao extends AbstractDao<Long, Street> {

	Long getCount();

	List<UserProfile> getAll(SingularAttribute<Street, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<UserProfile> getAllByName(String name);
}
