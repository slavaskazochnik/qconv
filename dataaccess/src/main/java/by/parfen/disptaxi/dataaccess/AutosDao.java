package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Autos;

public interface AutosDao extends AbstractDao<Long, Autos> {

	Long getCount();

	List<Autos> getAll(SingularAttribute<Autos, ?> attr, boolean ascending, int startRecord, int pageSize);
}
