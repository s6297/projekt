package org.mpr.jakub.winiarski.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mpr.jakub.winiarski.exceptions.CommentIsNullException;
import org.mpr.jakub.winiarski.exceptions.PostIsNullException;
import org.mpr.jakub.winiarski.model.Blog;
import org.mpr.jakub.winiarski.model.Comment;
import org.mpr.jakub.winiarski.model.Post;


public class FillDatabseForTest {
    public Session fill() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Blog blog = new Blog();
        blog.setSubject("Temat bloga");
        blog.setHeader("Nagłówek bloga");

        Post post = new Post("Temat posta");
        post.setContent("Treść posta");
        post.setAuthor("Autor");
        
        Comment comment = new Comment("Treść komentarza", "Autor");
        
        try {
            blog.addPost(post);
            post.addComment(comment);
        } catch (CommentIsNullException ex) {
            Logger.getLogger(FillDatabseForTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PostIsNullException ex) {
            Logger.getLogger(FillDatabseForTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.save(blog);
        session.save(post);
        session.save(comment);
        
        tx.commit();
        
        return sessionFactory.getCurrentSession();
    }
}
