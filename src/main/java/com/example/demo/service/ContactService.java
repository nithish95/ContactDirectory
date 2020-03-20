package com.example.demo.service;

import com.example.demo.dao.ContactDAO;
import com.example.demo.dto.NewContactDTO;
import com.example.demo.model.Address;
import com.example.demo.model.Contact;
import com.example.demo.model.Date;
import com.example.demo.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;

@Service
@Configurable
public class ContactService {
    @Autowired
    private ContactDAO contactDAO;
    public HashSet<Contact> search(String searchString){
        return contactDAO.searchContact(searchString);
    }

    public boolean addContact(NewContactDTO newContactDTO){
        return contactDAO.addContact(newContactDTO);
    }

    public boolean deleteContact(int id){
        return contactDAO.deleteContact(id);
    }

    public NewContactDTO getFullDetails(int id){
        NewContactDTO newContactDTO = new NewContactDTO();
        Contact contact = contactDAO.getContact(id);
        newContactDTO.setFname(contact.getfName());
        newContactDTO.setMname(contact.getmName());
        newContactDTO.setLname(contact.getlName());
        List<Address> addressList = contactDAO.getAddress(id);
        newContactDTO.setAddresses(addressList);
        List<Phone> phoneList = contactDAO.getPhone(id);
        newContactDTO.setPhones(phoneList);
        List<Date> dateList = contactDAO.getDate(id);
        newContactDTO.setDates(dateList);
        return newContactDTO;
    }

}
