package org.mpr.jakub.winiarski;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mpr.jakub.winiarski.exceptions.CommentIsNullException;
import org.mpr.jakub.winiarski.exceptions.PostIsNullException;
import org.mpr.jakub.winiarski.model.Blog;
import org.mpr.jakub.winiarski.model.Comment;
import org.mpr.jakub.winiarski.model.Post;
import org.mpr.jakub.winiarski.util.FillDatabseForTest;
import org.mpr.jakub.winiarski.util.HibernateUtil;

public class Main 
{
    public static void main( String[] args )
    {
        System.out.println("Start Main method");
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Blog blog = new Blog();
        blog.setSubject("Temat bloga");
        blog.setHeader("Nagłówek bloga");

        Post firstPost = new Post("Temat pierwszego posta");
        firstPost.setContent("Treść pierwszego posta");
        firstPost.setAuthor("Pierwszy autor posta");
        
        Post secondPost = new Post("Temat drugiego posta");
        secondPost.setContent("Treść drugiego posta");
        secondPost.setAuthor("Drugi autor posta");
        
        Comment firstComment = new Comment("Treść pierwszego komentarza", "Pierwszy autor komentarza");
        Comment secondComment = new Comment("Treść drugiego komentarza", "Drugi autor komentarza");
        
        try {
            blog.addPost(firstPost);
            blog.addPost(secondPost);
            firstPost.addComment(firstComment);
            firstPost.addComment(secondComment);
        } catch (CommentIsNullException ex) {
            Logger.getLogger(FillDatabseForTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PostIsNullException ex) {
            Logger.getLogger(FillDatabseForTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.save(blog);
        session.save(firstPost);
        session.save(secondPost);
        session.save(firstComment);
        session.save(secondComment);
        
        tx.commit();
        
        System.out.println("----- Wyniki wyszukiwania -----");
        
        Browser browser = new Browser();
        
        System.out.println("--- Posty bez komentarzy");
        List<Post> posts = browser.getPostWithoutComments();
        
        for(Post post : posts) {
            System.out.println(post.getSubject() + " ---- " + post.getAuthor());
        }
        
        System.out.println("--- Posty z tematem: \"Temat pierwszego posta\" ");
        posts = browser.searchPostsBySubject("Temat pierwszego posta");
        
        for(Post post : posts) {
            System.out.println(post.getSubject() + " ---- " + post.getAuthor());
        }
        
        System.out.println("--- Komentarze do posta o temacie: \"Temat pierwszego posta\" ");
        List<Comment> comments = browser.getCommentsFrom(firstPost);
        
        for(Comment comment : comments) {
            System.out.println(comment.getContent() + " ---- " + comment.getAuthor());
        }
    }
}