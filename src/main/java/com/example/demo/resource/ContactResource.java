package com.example.demo.resource;

import com.example.demo.dto.NewContactDTO;
import com.example.demo.model.Address;
import com.example.demo.model.Contact;
import com.example.demo.model.Date;
import com.example.demo.model.Phone;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/contacts")
public class ContactResource {

    @Autowired
    ContactService contactService;

    @GetMapping(value ="/getFullDetails/{id}")
    public NewContactDTO getFullDetails(@PathVariable("id") int id){
        return contactService.getFullDetails(id);
    }

    @GetMapping(value ="/search/{searchString}")
    public HashSet<Contact> searchContact(@PathVariable("searchString") String searchString){
        return contactService.search(searchString);
    }

    @PostMapping(value="/addContact")
    public ResponseEntity<Void> addContact(@RequestBody NewContactDTO newContactDTO){
        if(contactService.addContact(newContactDTO))
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        else
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value="/updateContact")
    public ResponseEntity<Void> update(@RequestBody NewContactDTO newContactDTO){
        if(contactService.update(newContactDTO))
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        else
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/deleteContact/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContact(@PathVariable("id") int id){
        if(contactService.deleteContact(id))
            return new ResponseEntity<Void>(HttpStatus.OK);
         else
             return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/deleteAddress/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") int id){
        if(contactService.deleteAddress(id))
            return new ResponseEntity<Void>(HttpStatus.OK);
        else
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/deletePhone/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePhone(@PathVariable("id") int id){
        if(contactService.deletePhone(id))
            return new ResponseEntity<Void>(HttpStatus.OK);
        else
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/deleteDate/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDate(@PathVariable("id") int id){
        if(contactService.deleteDate(id))
            return new ResponseEntity<Void>(HttpStatus.OK);
        else
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
