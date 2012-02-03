package org.mpr.jakub.winiarski.test;


import org.mpr.jakub.winiarski.model.Post;
import org.mpr.jakub.winiarski.util.FillDatabseForTest;
import org.hibernate.Transaction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mpr.jakub.winiarski.model.Comment;
import static org.junit.Assert.*;

public class CommentTest {
    
    private Session session;
    
    @Before
    public void setUp() {
        FillDatabseForTest databseForTest = new FillDatabseForTest();
        session = databseForTest.fill();
    }
    
    @Test
    public void persistComment() {
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Comment.class);
        assertEquals(1, criteria.list().size());
        
        tx.commit();
    }
    
    @Test
    public void setUpComment() {
        Comment comment = new Comment("Komentarz", "Autor");
        Post post = new Post("Temat");
        comment.setPost(post);
        comment.setId(1L);
        
        assertEquals("Komentarz", comment.getContent());
        assertEquals("Autor", comment.getAuthor());
        assertSame(post, comment.getPost());
        assertEquals(new Long(1), comment.getId());
    }
    
}
