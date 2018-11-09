package database;

import java.util.List;

public interface DataBase {

	public void write(UrlEntity entity);

	public List<UrlEntity> read();
}
