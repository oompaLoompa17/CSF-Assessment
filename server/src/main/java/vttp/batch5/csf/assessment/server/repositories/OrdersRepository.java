package vttp.batch5.csf.assessment.server.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class OrdersRepository {
  @Autowired private MongoTemplate mongoTemplate;

  public static final String C_MENUS = "menus";
  
  // TODO: Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  //  Native MongoDB query here
  /*
    * db.menus.find().sort({name: 1})
    */
  public List<Document> getMenu(){
      Query query = Query.query(Criteria.where(""))
          .with(Sort.by(Sort.Direction.ASC, "name"));

      List<Document> docs = mongoTemplate.find(query, Document.class, C_MENUS);
      return docs;
  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  //  Native MongoDB query here
  
}
