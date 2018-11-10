package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import resources.Config;

public class MongoInteractor implements DataBase {
	private String mongoDbConnectionString;
	private String mongoDbDataBaseName;
	private String mongoDbCollectionName;

	private static MongoInteractor mongoInteractorInstance;
	private MongoClient mongoClient;

	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> mongoCollection;

	private MongoInteractor() {
		try {
			mongoDbConnectionString = Config.getInstance().getProperty("mongoDbConnectionString");
			mongoDbDataBaseName = Config.getInstance().getProperty("mongoDbCollectionName");
			mongoDbCollectionName = Config.getInstance().getProperty("mongoDbCollectionName");
			initConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MongoInteractor getInstance() {
		if (mongoInteractorInstance == null)
			synchronized (MongoInteractor.class) {
				if (mongoInteractorInstance == null) {
					mongoInteractorInstance = new MongoInteractor();
				}
			}
		return mongoInteractorInstance;
	}

	private void initConnection() {

		try {
			mongoClient = new MongoClient(new MongoClientURI(mongoDbConnectionString));
			mongoDatabase = mongoClient.getDatabase(mongoDbDataBaseName);
			mongoCollection = mongoDatabase.getCollection(mongoDbCollectionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void write(UrlEntity entity) {
		Map<String, Object> valuesMap = new HashMap<String, Object>();
		valuesMap.put("name", entity.getName());
		valuesMap.put("url", entity.getUrl());
		try {
			mongoCollection.insertOne(new Document(valuesMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized List<UrlEntity> read() {
		List<UrlEntity> urlEntities = new ArrayList<UrlEntity>();
		try (MongoCursor<Document> entitiesIterator = mongoCollection.find().iterator()) {
			while (entitiesIterator.hasNext()) {
				Document document = entitiesIterator.next();
				urlEntities.add(new UrlEntity((String) document.get("name"), (String) document.get("url")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return urlEntities;

		}
		return urlEntities;
	}
}
