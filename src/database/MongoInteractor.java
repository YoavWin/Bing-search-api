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
	private static String mongoDbConnectionString;
	private static String mongoDbDataBaseName;
	private static String mongoDbCollectionName;
	private static MongoInteractor mongoInteractorInstance;
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static MongoCollection<Document> mongoCollection;

	private MongoInteractor() {
		mongoDbConnectionString = Config.getInstance().getProperty("mongoDbConnectionString");
		mongoDbDataBaseName = Config.getInstance().getProperty("mongoDbCollectionName");
		mongoDbCollectionName = Config.getInstance().getProperty("mongoDbCollectionName");
	}

	public static MongoInteractor getInstance() {
		if (mongoInteractorInstance == null)
			synchronized (MongoInteractor.class) {
				if (mongoInteractorInstance == null)
					initMongoInteractor();
			}
		return mongoInteractorInstance;
	}

	private static void initMongoInteractor() {
		mongoInteractorInstance = new MongoInteractor();
		mongoClient = new MongoClient(new MongoClientURI(mongoDbConnectionString));
		mongoDatabase = mongoClient.getDatabase(mongoDbDataBaseName);
		mongoCollection = mongoDatabase.getCollection(mongoDbCollectionName);
	}

	public synchronized void write(UrlEntity entity) {
		Map<String, Object> valuesMap = new HashMap<String, Object>();
		valuesMap.put("name", entity.getName());
		valuesMap.put("url", entity.getUrl());
		mongoCollection.insertOne(new Document(valuesMap));
	}

	public synchronized List<UrlEntity> read() {
		List<UrlEntity> urlEntities = new ArrayList<UrlEntity>();
		MongoCursor<Document> entitiesIterator = mongoCollection.find().iterator();

		while (entitiesIterator.hasNext()) {
			Document document = entitiesIterator.next();
			urlEntities.add(new UrlEntity((String) document.get("name"), (String) document.get("url")));
		}
		return urlEntities;
	}
}
