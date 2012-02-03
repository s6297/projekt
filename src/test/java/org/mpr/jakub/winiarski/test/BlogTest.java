package org.mpr.jakub.winiarski.test;

import java.util.List;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.mpr.jakub.winiarski.exceptions.PostIsNullException;
import org.mpr.jakub.winiarski.model.Blog;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mpr.jakub.winiarski.model.Post;
import org.mpr.jakub.winiarski.util.FillDatabseForTest;
import static org.junit.Assert.*;

public class BlogTest {
    
    private Session session;
    
    @Before
    public void setUp() {
        FillDatabseForTest databseForTest = new FillDatabseForTest();
        session = databseForTest.fill();
    }

    @Test
    public void isBlogPersist() {
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Blog.class);
        assertEquals(1, criteria.list().size());
        
        tx.commit();
    }
    
    @Test
    public void setUpBlog() {
        List<Post> posts = new ArrayList<Post>();
        Blog blog = new Blog();
        blog.setId(new Long(1));
        blog.setHeader("Nagłówek");
        blog.setSubject("Temat");
        blog.setPosts(posts);
        
        assertEquals(new Long(1), blog.getId());
        assertEquals("Nagłówek", blog.getHeader());
        assertEquals("Temat", blog.getSubject());
        assertSame(posts, blog.getPosts());
    }
    
    @Test
    public void getNotInitPosts() {
        Blog blog = new Blog();
        assertNotNull(blog.getPosts());
        assertEquals(0, blog.getPosts().size());
    }
    
    @Test
    public void addPost() throws PostIsNullException {
        Blog blog = new Blog();
        blog.addPost(new Post("Temat"));
        
        assertEquals(1, blog.getPosts().size());
        assertSame(blog, blog.getPosts().get(0).getBlog());
    }
    
    @Test (expected=PostIsNullException.class)
    public void addNullPost() throws PostIsNullException {
        Blog blog = new Blog();
        blog.addPost(null);
    }
}
