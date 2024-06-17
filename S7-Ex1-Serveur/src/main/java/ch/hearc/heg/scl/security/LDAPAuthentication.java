package ch.hearc.heg.scl.security;

public class LDAPAuthentication {
    public static boolean checkUser(String username, String password){
        LDAPObject ldap = new LDAPObject();
        ldap.connect(username, password);
        if(ldap.isConnected()){
            System.out.println("Utilisateur connecté");
            ldap.disconnect();
            return true;
        }else{
            System.out.println("Utilisateur non connecté - erreur d'authentification");
            return false;
        }
    }
}
