package by.parfen.disptaxi.dataaccess;

public interface AbstractDao<ID, Entity> {

	Entity getById(ID id);
}
