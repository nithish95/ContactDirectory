package com.example.demo.dao;

import com.example.demo.dto.NewContactDTO;
import com.example.demo.model.Address;
import com.example.demo.model.Contact;
import com.example.demo.model.Date;
import com.example.demo.model.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
@Transactional
public class ContactDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public HashSet<Contact> searchContact(String searchString){
        Query query1 = entityManager.createQuery("" +
                "Select c " + "from Contact c " + "where c.fName LIKE '%"+searchString+"%' " +
                "OR c.mName LIKE '%"+searchString+"%'" +
                "OR c.lName LIKE '%"+searchString+"%'" +
                "");
        Query query2 = entityManager.createQuery("" +
                 "Select contact " + "from Address a " + "where a.address LIKE '%"+searchString+"%' " +
                "OR a.city LIKE '%"+searchString+"%' "+
                "OR a.addressType LIKE '%"+searchString+"%' "+
                "OR a.state LIKE '%"+searchString+"%' "+
                "OR a.zip LIKE '%"+searchString+"%'"+
                "");
        Query query3 = entityManager.createQuery("" +
                "Select contact " + "from Phone p " + "where p.phoneType LIKE '%"+searchString+"%' "+
                "OR p.number LIKE '%"+searchString+"%' "+
                "OR p.areaCode LIKE '%"+searchString+"%'"+
                "");
        Query query4 = entityManager.createQuery("" +
                "Select contact " + "from Date d " + "where d.dateType LIKE '%"+searchString+"%' "+
                "OR d.date LIKE '%"+searchString+"%'"+
                "");
        HashSet<Contact> combinedResult = new HashSet<Contact>(query1.getResultList());
        combinedResult.addAll(query2.getResultList());
        combinedResult.addAll(query3.getResultList());
        combinedResult.addAll(query4.getResultList());
        return combinedResult;
    }

    public Contact getContact(int id){
        Query query = entityManager.createQuery("" +
                "Select c " + "from Contact c " + "where c.contactId = "+id+
                "");
        return (Contact) query.getSingleResult();
    }

    public List<Address> getAddress(int id){
        Query query = entityManager.createQuery("" +
                "Select a " + "from Address a " + "where a.contact.contactId = "+id+
                "");
        return query.getResultList();
    }

    public List<Phone> getPhone(int id){
        Query query = entityManager.createQuery("" +
                "Select p " + "from Phone p " + "where p.contact.contactId = "+id+
                "");
        return query.getResultList();
    }

    public List<Date> getDate(int id){
        Query query = entityManager.createQuery("" +
                "Select d " + "from Date d " + "where d.contact.contactId = "+id+
                "");
        return query.getResultList();
    }

    public void updateAddress(Session session,Address address){
        Query query = session.createQuery("update Address set " +
                "address = :address, city = :city, state = :state, zip = :zip, addressType = :addressType"+
                " where addressId = :addressId");
        query.setParameter("addressId", address.getAddressId());
        query.setParameter("city",address.getCity());
        query.setParameter("state",address.getState());
        query.setParameter("addressType",address.getAddressType());
        query.setParameter("address",address.getAddress());
        query.setParameter("zip",address.getZip());
        query.executeUpdate();
    }

    public void updateContact(Session session,NewContactDTO newContactDTO){
        Query query = session.createQuery("update Contact set " +
                "fName = :fName, mName = :mName, lName = :lName"+
                " where contactId = :contactId");
        query.setParameter("fName", newContactDTO.getFname());
        query.setParameter("mName",newContactDTO.getMname());
        query.setParameter("lName",newContactDTO.getLname());
        query.setParameter("contactId",newContactDTO.getContactId());
        query.executeUpdate();
    }

    public boolean update(NewContactDTO newContactDTO){
        Configuration config = new Configuration();
        SessionFactory sessionFactory = config.configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if(newContactDTO.getFname()!=null || newContactDTO.getMname()!=null || newContactDTO.getLname()!=null){
                updateContact(session,newContactDTO);
            }
            List<Address> addressList = newContactDTO.getAddresses();
            if (addressList != null) {
                for (int i = 0; i < addressList.size(); i++) {
                    if (addressList.get(i).getAddressId() > 0) {
                        updateAddress(session, addressList.get(i));
                    } else {
                        session.save(addressList.get(i));
                    }
                }
            }

            List<Phone> phoneList = newContactDTO.getPhones();
            if(phoneList!=null) {
                for (int i = 0; i < phoneList.size(); i++) {
                    if (phoneList.get(i).getPhoneId() > 0) {
                        updatePhone(session, phoneList.get(i));
                    } else {
                        session.save(phoneList.get(i));
                    }
                }
            }

            List<Date> dateList = newContactDTO.getDates();
            if(dateList!=null) {
                for (int i = 0; i < dateList.size(); i++) {
                    if (dateList.get(i).getDateId() > 0) {
                        updateDate(session, dateList.get(i));
                    } else {
                        session.save(dateList.get(i));
                    }
                }
            }
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            System.out.println("Error occured while Updation ---"+e);
            return false;
        }
    }

    public void updatePhone(Session session,Phone phone){
            Query query = session.createQuery("update Phone set " +
                    "number = :number, phoneType = :phoneType, areaCode = :areaCode "+
                    " where phoneId = :phoneId");
            query.setParameter("phoneId", phone.getPhoneId());
            query.setParameter("areaCode",phone.getAreaCode());
            query.setParameter("number",phone.getNumber());
            query.setParameter("phoneType",phone.getPhoneType());
            query.executeUpdate();
    }

    public void updateDate(Session session,Date date){
            Query query = session.createQuery("update Date set " +
                    "date = :date, dateType = :dateType"+
                    " where dateId = :dateId");
            query.setParameter("dateId", date.getDateId());
            query.setParameter("date",date.getDate());
            query.setParameter("dateType",date.getDateType());
            query.executeUpdate();
    }

    public boolean deleteAddress(int id){
        Configuration config = new Configuration();
        SessionFactory sessionFactory = config.configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "Delete from Address a where a.addressId="+id;
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error occured while address deletion ---"+e);
            return false;
        }
    }

    public boolean deletePhone(int id){
        Configuration config = new Configuration();
        SessionFactory sessionFactory = config.configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "Delete from Phone p where p.phoneId="+id;
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error occured while phone deletion ---"+e);
            return false;
        }
    }

    public boolean deleteDate(int id){
        Configuration config = new Configuration();
        SessionFactory sessionFactory = config.configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "Delete from Date d where d.dateId="+id;
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error occured while date deletion ---"+e);
            return false;
        }
    }

    public boolean deleteContact(int id){
        Configuration config = new Configuration();
        SessionFactory sessionFactory = config.configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "Delete from Contact c where c.contactId="+id;
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error occured while deletion ---"+e);
            return false;
        }
    }

    public boolean addContact(NewContactDTO newContactDTO){
        Configuration config = new Configuration();
        SessionFactory sessionFactory = config.configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Contact contact = new Contact();
            contact.setfName(newContactDTO.getFname());
            contact.setmName(newContactDTO.getMname());
            contact.setlName(newContactDTO.getLname());
            //System.out.println(contact.getContactId());
            session.save(contact);
            List<Address> addressList = new ArrayList<>();
            if(newContactDTO.getAddresses()!=null){
                addressList = newContactDTO.getAddresses();
            }
            for(int i=0;i<addressList.size();i++){
                Address address = new Address();
                address.setContact(contact);
                address.setAddress(addressList.get(i).getAddress());
                address.setAddressType(addressList.get(i).getAddressType());
                address.setCity(addressList.get(i).getCity());
                address.setState(addressList.get(i).getState());
                address.setZip(addressList.get(i).getZip());
                session.save(address);
            }
            List<Phone> phoneList = new ArrayList<>();
            if(newContactDTO.getPhones()!=null) {
                phoneList = newContactDTO.getPhones();
            }
            for(int i=0;i<phoneList.size();i++){
                Phone phone = new Phone();
                phone.setContact(contact);
                phone.setAreaCode(phoneList.get(i).getAreaCode());
                phone.setNumber(phoneList.get(i).getNumber());
                phone.setPhoneType(phoneList.get(i).getPhoneType());
                session.save(phone);
            }
            List<Date> dateList = new ArrayList<>();
            if(newContactDTO.getDates()!=null){
                dateList = newContactDTO.getDates();
            }
            for(int i=0;i<dateList.size();i++){
                Date date = new Date();
                date.setContact(contact);
                date.setDate(dateList.get(i).getDate());
                date.setDateType(dateList.get(i).getDateType());
                session.save(date);
            }
            tx.commit();
            return true;
        }
        catch (Exception e){
            tx.rollback();
            System.out.println("Error Occurred during Insertion ---- "+e);
            return false;
        }
        finally {
            session.close();
        }
    }

}
