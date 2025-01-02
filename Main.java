import dao.UserDAOImpl;
import model.UserBase;

public class Main {
    public static void main(String[] args) {
        try {
            UserDAOImpl userDAO = new UserDAOImpl();
            UserBase user = userDAO.findById(1);
            if (user != null) {
                System.out.println("User found: " + user.getName());
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
