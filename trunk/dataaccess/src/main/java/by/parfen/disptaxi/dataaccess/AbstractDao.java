package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

public interface AbstractDao<ID, Entity> {

	Entity getById(ID id);

	List<Entity> getAll();

	void delete(ID key);

	void deleteAll();

	void delete(List<ID> ids);

	List<Entity> getAllByFieldRestriction(final SingularAttribute<? super Entity, ?> attribute, final Object value);

	Entity insert(Entity entity);

	Entity update(Entity entity);
}
