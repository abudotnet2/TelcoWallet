/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Services;

import MtnWallet.Entities.AuditTrail;
import MtnWallet.Entities.PersonalInfo;
import MtnWallet.Entities.Transactions;
import MtnWallet.Entities.Users;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Transaction;

/**
 *
 * @author omolajaabubakar
 */
@Stateless
public class DBManager {

    @PersistenceContext(unitName = "com.Flutterwave_mtnwallet_war_1.0PU")
    private EntityManager em;

    // Audit trail repository
    public int addAuditTrailToDB(String apiName, String phoneNo, String xmlRequestMessage, String telcoResponse) {
        AuditTrail audit = new AuditTrail();

        audit.setApiName(apiName);
        audit.setCreatedDate(Date.valueOf(LocalDate.now()));
        audit.setPhoneNo(phoneNo);
        audit.setXmlRequest(xmlRequestMessage);
        audit.setStatus(1); // successful;
        audit.setXmlResponse(telcoResponse);

        // persist to db..
        addAuditTrail(audit);
        return 0;
    }

    private int addAuditTrail(AuditTrail entity) {
        em.persist(entity);
        return 1;
    }

    public int updateAuditTrail(int id, String status) {

        // https://www.youtube.com/watch?v=ncvj2B0KDRU
        int response = em.createQuery("update AuditTrail a set s.Status=:status where Id=:id ")
                .setParameter("status", status).setParameter("Id", id).executeUpdate();
        return response;
    }

    public List<AuditTrail> getAuditTrail() {
        String sqlString = "select * from AuditTrail";
        List query = em.createQuery(sqlString).getResultList();
        return query;
    }

    public int addTransactionToDb(String phoneNo, BigDecimal amount, String appMessage,
            String providerTranxId, int providerStatus, String telcoTranxId, int telcoStatus) {
        Transactions tranx = new Transactions();

        tranx.setPhoneno(phoneNo);
        tranx.setAmount(amount);
        tranx.setTelcoTransactionId(telcoTranxId);
        tranx.setTelcoStatus(telcoStatus);
        tranx.setProviderStatus(providerStatus);
        tranx.setProviderTransactionId(providerTranxId);
        tranx.setAppResponseMessage(appMessage);

        addTransaction(tranx);
        return 1;
    }

    // Add transaction 
    private int addTransaction(Transactions entity) {

        em.persist(entity);
        return 1;
    }

    public int updateTransaction(int id, String status) {
        int response = em.createQuery("update Transaction a set s.Status=:status where Id=:id ")
                .setParameter("status", status).setParameter("Id", id).executeUpdate();
        return response;
    }

    // Add Personal  information .. 
    public int addPersonalInfo(PersonalInfo entity) {
        em.persist(entity);
        return 1;
    }

    public int updatePersonalInfo(int id, String status) {
        int response = em.createQuery("update PersonalInfo a set s.Status=:status where Id=:id ")
                .setParameter("status", status).setParameter("Id", id).executeUpdate();
        return response;
    }

    // Addd User ..
    public int AddUser(Users entity) {
        em.persist(entity);
        return 1;
    }

    public boolean IsValidUser(String username, String password) {
        String sql = "select u from Users u where u.username = :username and u.password = :password";
            
        int query = em.createQuery(sql)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList().toArray().length;
                

        //query.get(0);
        return query > 0;
    }

//    public List<BookEntity> findAll() {
//	EntityManager em = connection.createEntityManager();
//	List<BookEntity> result;
//	
//	try {
//		em.getTransaction().begin();
//		TypedQuery<BookEntity> query = em.createQuery("Select b from BookEntity b", BookEntity.class);
//		result = query.getResultList();
//		em.getTransaction().commit();
//	} finally {
//		if (em.getTransaction().isActive()) {
//			em.getTransaction().rollback();
//		}
//		em.close();
//	}
//	
//	return result;
//}
}
