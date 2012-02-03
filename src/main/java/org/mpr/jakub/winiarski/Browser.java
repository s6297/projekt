package org.mpr.jakub.winiarski;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mpr.jakub.winiarski.model.Comment;
import org.mpr.jakub.winiarski.model.Post;
import org.mpr.jakub.winiarski.util.HibernateUtil;

public class Browser {

    public List<Post> getPostWithoutComments() {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(Post.class);
        List<Post> posts = criteria.list();
        List<Post> result = new ArrayList<Post>(); 
        
        for(Post post : posts) {
            if(post.getComments().isEmpty()) {
                result.add(post);
            }
        }
        
        tx.commit();

        return result;
    }

    public List<Comment> getCommentsFrom(Post post) {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(Comment.class);
        criteria.add(Restrictions.eq("post", post));
        List<Comment> comments = criteria.list();
        
        tx.commit();

        return comments;
    }

    public List<Post> searchPostsBySubject(String subject) {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(Post.class);
        criteria.add(Restrictions.eq("subject",subject));
        List<Post> posts = criteria.list();

        tx.commit();

        return posts;
    }

    private Session getCurrentSession() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return sessionFactory.getCurrentSession();
    }
}
