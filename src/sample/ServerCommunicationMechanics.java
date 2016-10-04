package sample;

import java.sql.*;
import java.util.ArrayList;

/**
 * Třída pro komunikaci mezi klientem a serverem
 *
 * Využívá MySQL driver od Oracle, volně stažitelný, užitečný program
 *
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * TODO pičááá překopat celou databázi do piče dubové kurva piča drát!!!!!!!!!!!!
 * TODO a taky optimalizovat kod na použití pravidla DRY
 * TODO upravit dotazy pro statistiky
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
class ServerCommunicationMechanics {

    /* zavedení globálních promennych */
    private String nick ="";
    private String email ="";
    private String password ="";

    /* zavedeni potrebnych trid */
    private ArrayList<Integer> statistics = new ArrayList<>();

    /**
     * Základní funkce pro přihlašování
     *
     * popis této funkce je rozepsán přes celou tuto funkci, což mělo jenom pozitivní věci
     * za 1. není jeden dlouhý komentár ale více malých, což je fajn to mít napsané přímo u toho volání
     * za 2. vypadá že jsem snad i něco udělal, a né jenom kouřil vodnice
     */
    boolean loginUser(String nick, String password, Boolean WebDatabase){
        this.nick = nick;
        try {
            if (!WebDatabase) {
                Connection conn;
                /*
                 * následující tři kroky jsou jednoduhé, prostě do Stringů uložím základní informace
                 * jako uživatele databáze, což je vždycky root, potom jeho heslo "Kobliha"
                 * a samotný název databáze, konkrétně Dickbutt
                 */
                String dbuser = "root";
                String dbpassw = "Kobliha";
                String databasename = "dickbutt";
                String url = "jdbc:mysql://localhost/" + databasename;      // kam se má tato třída pokusit připojit
                Class.forName("com.mysql.jdbc.Driver");                     // mySQL driver, užitečná to věc
                conn = DriverManager.getConnection(url, dbuser, dbpassw);  // nastavení připojení
                Statement st = conn.createStatement();
                /*
                 * ověření, že uživatel a heslo souhlasí tím, že se tato funkce připojí k databázi a pokusí se ho vyhledat
                 *
                 * jo, může to být lepší, ale jako BETA!!!! test to funguje dobře
                 */
                ResultSet res = st.executeQuery("SELECT * FROM users where nick='" + nick + "' and password = '" + password + "'");
                /*
                 * vyhodnocení a návratové funkce
                 */
                if (res.next()) {
                    System.out.println("Login Succesful");
                } else {
                    System.out.println("something went horribly wrong");
                    return false;
                }
            } else {
                System.out.println("Připojuji na databázi uloženou na netu, proto to bude trvat");
                Connection conn;
                /*
                 * následující tři kroky jsou jednoduhé, prostě do Stringů uložím základní informace
                 * jako uživatele databáze, což je vždycky root, potom jeho heslo "Kobliha"
                 * a samotný název databáze, konkrétně Dickbutt
                 */
                String dbuser = "czrydlis";
                String dbpassw = "DA90B79100";
                String databasename = "czrydlis";
                String url = "jdbc:mysql://nibiru.zarea.net/sqladmin" + databasename;      // kam se má tato třída pokusit připojit
                Class.forName("com.mysql.jdbc.Driver");                     // mySQL driver, užitečná to věc
                conn = DriverManager.getConnection(url, dbuser, dbpassw);  // nastavení připojení
                Statement st = conn.createStatement();
                /*
                 * ověření, že uživatel a heslo souhlasí tím, že se tato funkce připojí k databázi a pokusí se ho vyhledat
                 *
                 * jo, může to být lepší, ale jako BETA!!!! test to funguje dobře
                 */
                ResultSet res = st.executeQuery("SELECT * FROM users where nick='" + nick + "' and password = '" + password + "'");
                /*
                 * vyhodnocení a návratové funkce
                 */
                if (res.next()) {
                    System.out.println("Login Succesful");
                } else {
                    System.out.println("something went horribly wrong");
                    return false;
                }
            }
            /*
             * když se něco posere, tohle mě na vše upozorní
             */
        } catch (SQLException e) {
            e.printStackTrace();
            new Dialogy().Error("Ooops!", "Can't connect to server, please try again in few minutes");
            return false;
        }  catch (ClassNotFoundException e){
            e.printStackTrace();
            new Dialogy().Error("Ooops!","Something is really fucked up bro");
            return false;
        }
        return true;
    }

    ArrayList<Integer> getStatistics(String nick, Boolean isWebDatabase) {
        System.out.println("funkce pro statistiky");
        System.out.println(nick);
        try {
            if (!isWebDatabase){
                System.out.println("není webová databáze");
                Connection conn;
                /*
                 * následující tři kroky jsou jednoduhé, prostě do Stringů uložím základní informace
                 * jako uživatele databáze, což je vždycky root, potom jeho heslo "Kobliha"
                 * a samotný název databáze, konkrétně Dickbutt
                 */
                String dbuser = "root";
                String dbpassw = "Kobliha";
                String databasename = "dickbutt";
                String url = "jdbc:mysql://localhost/" + databasename;      // kam se má tato třída pokusit připojit
                Class.forName("com.mysql.jdbc.Driver");                     // mySQL driver, užitečná to věc
                conn = DriverManager.getConnection(url, dbuser, dbpassw);  // nastavení připojení
                Statement st = conn.createStatement();
                System.out.println(nick + " -> VYPIČENÝ ZPIČENÝ DO PIČE NICK KURVA PIČA");
                String sql = ("SELECT * FROM users WHERE nick = '"+nick+"'");
                System.out.println(sql);
                System.out.println("ziskani dat z databaze");
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    int XP = rs.getInt("xp");
                    statistics.add(XP);
                    System.out.println(XP);

                    int level = rs.getInt("level");
                    statistics.add(level);

                    int kills = rs.getInt("kills");
                    statistics.add(kills);

                    int deaths = rs.getInt("deaths");
                    statistics.add(deaths);

                    int game_money = rs.getInt("money");
                    statistics.add(game_money);

                    int played_hours = rs.getInt("played_hours");
                    statistics.add(played_hours);

                /*
                Date last_played = rs.getDate("last_played");
                statistics.add(last_played.to)
                */
                }
            } if(isWebDatabase) {
                System.out.println("je webova databaze");
                Connection conn;
                /*
                 * následující tři kroky jsou jednoduhé, prostě do Stringů uložím základní informace
                 * jako uživatele databáze, což je vždycky root, potom jeho heslo "Kobliha"
                 * a samotný název databáze, konkrétně Dickbutt
                 */
                String dbuser = "czrydlis";
                String dbpassw = "DA90B79100";
                String databasename = "czrydlis";
                String url = "jdbc:mysql://nibiru.zarea.net/sqladmin" + databasename;      // kam se má tato třída pokusit připojit
                Class.forName("com.mysql.jdbc.Driver");                     // mySQL driver, užitečná to věc
                conn = DriverManager.getConnection(url, dbuser, dbpassw);  // nastavení připojení
                Statement st = conn.createStatement();
                System.out.println(nick + " ---> VYPIČENÝ ZPIČENÝ NIK KTERÝ SE NEZOBRAZUJE");
                String sql = ("SELECT * FROM users WHERE nick = '"+nick+"'");
                System.out.println("ziskani všech statistiky");
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    int XP = rs.getInt(3);
                    statistics.add(XP);

                    int level = rs.getInt(4);
                    statistics.add(level);

                    int kills = rs.getInt(5);
                    statistics.add(kills);

                    int deaths = rs.getInt(5);
                    statistics.add(deaths);

                    int game_money = rs.getInt(7);
                    statistics.add(game_money);

                    int played_hours = rs.getInt(8);
                    statistics.add(played_hours);

                /*
                Date last_played = rs.getDate("last_played");
                statistics.add(last_played.to)
                */
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            new Dialogy().Error("Ooops!", "Can't connect to server, please try again in few minutes");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Dialogy().Error("Ooops!", "Something is really fucked up bro");
        }
        return statistics;
    }

    /** gettery a settery
     *
     *
     * */
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
