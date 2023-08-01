package com.vc.kanbanProject.service;


import com.vc.kanbanProject.domain.DbSequence;
//import com.vc.kanbanUser.domain.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorPRJ {
    @Autowired
    private MongoOperations mongoOperations;

    public int getSequenceNumber(String seqName){
        Query query = new Query(Criteria.where("id").is(seqName));
        Update update = new Update().inc("prj",1);
        DbSequence sequence = mongoOperations.findAndModify(query,update,options().returnNew(true).upsert(true), DbSequence.class);
        return !Objects.isNull(sequence) ? sequence.getPrj() : 1;
    }
}
