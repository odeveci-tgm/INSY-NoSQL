package main;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
public class Operations {
	static MongoClient mongoClient = new MongoClient("localhost", 27017);
	static MongoDatabase db = mongoClient.getDatabase("studentsDb");
	static MongoCollection<Document> collection = db.getCollection("students");
	public static void insert() {
	
		
		JSONObject[] arr = new JSONObject[100];
		
		
		
		String[] klassen = {"4chit","4dhit"};
		String[] farben = {"rot","gelb","gruen"};
		
		for (int i=0;i<arr.length;i++) {
		int randomJahr = ThreadLocalRandom.current().nextInt(1999,2003);
		int randomIndexKlasse = ThreadLocalRandom.current().nextInt(0,2);
		int randomIndexFarbe = ThreadLocalRandom.current().nextInt(0,3);
		JSONObject json = new JSONObject();
		json
		.put("name", "Schueler"+i)
		.put("jahr", Integer.toString(randomJahr))
		.put("klasse", klassen[randomIndexKlasse]);
		JSONArray ampelarr = new JSONArray();
		JSONObject ampelitemAM = new JSONObject();
		JSONObject ampelitemINSY = new JSONObject();
		ampelitemAM.put("fach","AM").put("farbe", farben[randomIndexFarbe]);
		ampelitemINSY.put("fach","INSY").put("farbe", farben[randomIndexFarbe]);
		ampelarr.put(ampelitemAM).put(ampelitemINSY);
		json.put("ampeln", ampelarr);
		arr[i] = json;
		}
		for (JSONObject obj : arr) {
			collection.insertOne(Document.parse(obj.toString()));
		}
	}
	
	public static void findAll() {
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		} finally {
		    cursor.close();
		}

	}
	
	public static void findAllWithYear2000() {
		MongoCursor<Document> cursor = collection.find(Filters.eq("jahr","2000")).iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		    
		} finally {
		    cursor.close();
		}
	}
	
	public static void findAllWithYear2000orGreater() {
		MongoCursor<Document> cursor = collection.find(Filters.gte("jahr","2000")).iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		    
		} finally {
		    cursor.close();
		}
	}
	
	public static void findAllWithYear2000andClass4DHIT() {
		MongoCursor<Document> cursor = collection.find(Filters.and(Filters.eq("jahr","2000"),Filters.eq("klasse","4dhit"))).iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		    
		} finally {
		    cursor.close();
		}
	}
	
	public static void findAllWithRedInAMandClass4DHIT() {
		MongoCursor<Document> cursor = collection.find(Filters.and(Filters.eq("jahr","2000"),Filters.eq("klasse","4dhit"))).iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		    
		} finally {
		    cursor.close();
		}
	}
	
	

	public static void main(String[] args) {
		insert();
		//Alle Schueler
		System.out.println("--------------------------------Alle Schueler-----------------------------");
		findAll();
		System.out.println("--------------------------------Alle Schueler mit Geburtsjahr 2000-----------------------------");
		findAllWithYear2000();
		System.out.println("--------------------------------Alle Schueler mit Geburtsjahr 2000 oder höher-----------------------------");
		findAllWithYear2000orGreater();
		System.out.println("--------------------------------Alle Schueler mit Geburtsjahr 2000 und Klasse 4dhit-----------------------------");
		findAllWithYear2000andClass4DHIT();
		System.out.println("--------------------------------Alle Schueler mit einer roten Ampel in AM der Klasse 4dhit-----------------------------");
	}
	

}
