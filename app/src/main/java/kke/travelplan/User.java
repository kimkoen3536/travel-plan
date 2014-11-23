package kke.travelplan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by K.eun on 2014-11-20.
 */
public class User{
private static SimpleDateFormat df = new SimpleDateFormat("yyyy. M. d");

private int id;

private String email;

private String password;

private String accountName;

private String name;

private Date birthDate;


        public User(String email, String password) {
            this.email= email;
            this.password = password;
        }

        public User(String email,String password,String accountName, String name, Date birthDate ) {
            this(email,password);
            this.birthDate = birthDate;
            this.accountName = accountName;
            this.name = name;
        }

    public User() {

    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() { return email;}

        public void  setEmail(String email) {this.email = email;}


        public String getPassword() { return password;}

        public void  setPassword(String password) {this.password = password;}

    public String getAccountName() {return  accountName;}
    public void setAccountName(String accountName) { this.accountName = accountName;}

        public String getName() { return name;}

        public void  setName(String name) {this.name = name;}


         public Date getBirthDate() { return  birthDate; }
         public void setBirthDate(Date birthDate) {this.birthDate = birthDate;}


    public String toJson() {

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(this);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("json 에러", e);
            }
        }
}

