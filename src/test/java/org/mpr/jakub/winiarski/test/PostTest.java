package org.mpr.jakub.winiarski.test;

import org.mpr.jakub.winiarski.util.FillDatabseForTest;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.mpr.jakub.winiarski.model.Post;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PostTest {
    
    private Session session;
    
    @Before
    public void setUp() {
        FillDatabseForTest databseForTest = new FillDatabseForTest();
        session = databseForTest.fill();
    }
    
    @Test
    public void isPostPersist() {
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Post.class);
        assertEquals(1, criteria.list().size());
        
        tx.commit();
    }
}
