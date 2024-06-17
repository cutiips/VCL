package ch.hearc.heg.scl.security;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class LDAPObject {
    private final String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private final String URL = "ldap://ldap.forumsys.com:389/dc=example,dc=com";
    private final String SECURITY_AUTHENTICATION = "simple";
    private final String BASEDN = "dc=example,dc=com";
    private DirContext ctx;

    public LDAPObject(){}

    public void connect(String username,String password) {
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, URL);
            env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
            env.put(Context.SECURITY_PRINCIPAL, "uid=" + username + "," + BASEDN);
            env.put(Context.SECURITY_CREDENTIALS, password);
            ctx = new InitialDirContext(env);
        }catch(NamingException ex){
            ex.printStackTrace();
        }
    }

    public void disconnect(){
        try{
            ctx.close();
        }catch(NamingException ex){
            ex.printStackTrace();
        }
    }

    public boolean isConnected(){
        if(ctx != null){
            return true;
        }else{
            return false;
        }
    }
}
