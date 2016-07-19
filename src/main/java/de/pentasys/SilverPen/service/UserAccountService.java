package de.pentasys.SilverPen.service;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import de.pentasys.SilverPen.model.User;

@Stateless
public class UserAccountService {
    
    @PersistenceUnit
    private EntityManager em;
    
    @Resource
    private UserTransaction utx;
    
    public void register(User user){
        try {
            utx.begin();
            
            em.persist(user);
            utx.commit();
        } catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
