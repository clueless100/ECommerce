import java.io.*; 
import java.util.*;

class Pair implements Serializable
{
    public int id;
    public int quan;
    public static final long serialVersionUID = 42L;
    public Pair(int a,int b)
    {
        id = a;
        quan = b;
    }
}

class Product implements Serializable
{
    protected int ProductId;
    protected String ProductName;
    protected double ProductPrice;
    public static final long serialVersionUID = 42L;

    public Product()
    {
        System.out.print("\n\t\tEnter Product Id: ");
        ProductId = project.sc.nextInt();
        System.out.print("\n\t\tEnter Product Name: ");
        ProductName = project.sc.next();
        System.out.print("\n\t\tEnter Product Price: ");
        ProductPrice = project.sc.nextDouble();
    }

    public void OutputProduct()
    {
        System.out.println("\n\t\tID: "+ProductId);
        System.out.println("\n\t\tName: "+ProductName);
        System.out.println("\n\t\tPrice: "+ProductPrice);
    }

    public int retProductId()
    {
        return ProductId;
    }

    public String retProductName()
    {
        return ProductName;
    }

    public double retProductPrice()
    {
        return ProductPrice;
    }
}

abstract class Person implements Serializable
{
    protected String Name;
    protected String Username;
    protected int phone;
    protected String password;
    public static final long serialVersionUID = 42L;
    //abstract void login();
}

class Customer extends Person implements Serializable
{
    //protected int CustomerId;
    public static final long serialVersionUID = 42L;

    public static ArrayList<Customer> CustList = new ArrayList<Customer>();

    public Customer()
    {
        System.out.print("\n\t\tEnter Name: ");
        Name = project.sc.next();
        System.out.print("\n\t\tEnter Username: ");
        Username = project.sc.next();
        System.out.print("\n\t\tEnter Phone Number: ");
        phone = project.sc.nextInt();
        System.out.print("\n\t\tEnter Password: ");
        password = project.sc.next();
        CustList.add(this);
    }

    public String retUsername()
    {
        return Username;
    }

    public String retpassword()
    {
        return password;
    }

    public static void Add_Customers_to_File()
    {
        try
        {
            FileOutputStream f = new FileOutputStream(new File("customers.dat"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            // Write objects to file
            for(int i=0;i<CustList.size();++i)
            o.writeObject(CustList.get(i));
            o.close();
            f.close();
        }
        catch (IOException e)
        {
            System.out.println("\n\t\tIO Exception");
            e.printStackTrace();
        }
    }

    public static void Get_Customers_from_File()
    {
        boolean f=true;
        try
        {
            FileInputStream fi = new FileInputStream(new File("customers.dat"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            while(f)
            {     
                Customer c = (Customer)oi.readObject();
                CustList.add(c);
            }
            oi.close();
            fi.close();
        }
        catch(EOFException e)
        {
            f = false;
        }
        catch (IOException e) 
        {
            System.out.println("\n\t\tIO Exception");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("\n\t\tClassNotFound exception");
            e.printStackTrace();
        }
    }

    public void HomePage()
    {
        project.clear_screen();
        boolean f = true;
        do
        {
            System.out.println("\n\n\t\t\tHOME PAGE\n\n");
            System.out.println("\t\t1.View Your Details\n");
            System.out.println("\t\t2.Place an Order\n");
            System.out.println("\t\t3.View Previous Orders\n");
            System.out.println("\t\t4.Back\n\n");
            System.out.print("\t\tEnter choice: ");
            int choice = project.sc.nextInt();
            switch(choice)
            {
                case 1: project.clear_screen();
                        this.Output_Customer();
                        try
                        {
                            System.in.read();
                        }
                        catch(IOException e)
                        {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                        this.HomePage();
                        break;

                case 2: order o = new order(this);
                        project.clear_screen();
                        o.PrintOrder();
                        try
                        {
                            System.in.read();
                        }
                        catch(IOException e)
                        {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                        this.HomePage();
                        break;

                case 3: order.Prev_Orders(this);
                        try
                        {
                            System.in.read();
                        }
                        catch(IOException e)
                        {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                        this.HomePage();
                        break;

                case 4: project.Main_Menu();
                        break;

                default : f = false;
            }
        }while(!f);
    }

    public void Output_Customer()
    {
        System.out.println("\n\t\tName: "+Name);
        System.out.println("\n\t\tUsername: "+Username);
        System.out.println("\n\t\tPhone: "+phone);
    }

    public static void login()
    {
        project.clear_screen();
        System.out.print("\n\t\tEnter Username: ");
        String u = project.sc.next();
        System.out.print("\n\t\tEnter Password: ");
        String pw = project.sc.next();

        boolean usernameExists = false,validCred = false;
        int x=-1;
        for(int i=0;i<CustList.size();++i)
        {
            if(CustList.get(i).retUsername().equals(u))
            {
                usernameExists = true;
                if(CustList.get(i).retpassword().equals(pw))
                {
                    validCred = true;
                    x = i;
                }
                break;
            }
        }

        if(validCred) CustList.get(x).HomePage();
        else 
        {
            if(!usernameExists) System.out.println("\n\t\tUsername does not exist!");
            else System.out.println("\n\t\tIncorrect Password!");
            System.out.print("\n\n\t\tPress x to exit, anything else to retry: ");
            String c = project.sc.next();
            if(c.charAt(0)=='x') 
            {
                return;
            }
            else login();
        }
    }

    public static void CustomersList()
    {
        for(int i=0;i<Customer.CustList.size();++i)
        Customer.CustList.get(i).Output_Customer();
    }

    public static void Customer_Menu()
    {
        project.clear_screen();
        boolean f = true;
        do
        {
            System.out.println("\n\t\tCustomer Menu\n\n\n");
            System.out.println("\t\t1.Login\n");
            System.out.println("\t\t2.Signup\n");
            System.out.println("\t\t3.Back\n\n");
            System.out.print("\t\tEnter choice: ");
            int choice = project.sc.nextInt();
            switch(choice)
            {
                case 1: Customer.login();
                        break;

                case 2: Customer c = new Customer();
                        project.Main_Menu();
                        break;

                case 3: project.Main_Menu();
                        break;

                default : f = false;
            }
        }while(!f);
    }
}

class Admin extends Person implements Serializable
{
    public static final long serialVersionUID = 42L;
    public Admin()
    {
        /*System.out.print("\n\t\tEnter Name: ");
        Name = project.sc.next();
        System.out.print("\n\t\tEnter Username: ");
        Username = project.sc.next();
        System.out.print("\n\t\tEnter Phone Number: ");
        phone = project.sc.nextInt();
        System.out.print("\n\t\tEnter Password: ");
        password = project.sc.next();*/
        Name = "admin";
        Username = "admin";
        phone = 0;
        password = "root";
    }

    public static void Add_Product()
    {
        project.clear_screen();
        System.out.println("\n\t\t\t\tEnter details of New Product!!");
        Product p = new Product();
        project.ProdList.add(p);
        System.out.println("\n\t\tProduct added successfully!!");
    }

    public static void Delete_Product()
    {
        System.out.print("\n\t\tEnter id: ");
        boolean f = false;
        int x = project.sc.nextInt();
        for(int i=0;i<project.ProdList.size();++i)
        {
            if(project.ProdList.get(i).retProductId()==x)
            {
                project.ProdList.remove(i);
                f = true;
                break;
            }
        }
        if(f) System.out.println("\n\t\tDeleted sucessfully!!");
        else System.out.println("\n\t\tProduct to be deleted not found!");
    }

    public static void login()
    {
        project.clear_screen();
        System.out.print("\t\tEnter Password to gain Admin Privileges:");
        String  p = project.sc.next();
        if(p.equals("root")) Admin_Menu();
        else
        {
            System.out.println("\n\t\tIncorrect Password!");
            System.out.print("\n\n\t\tPress x to exit, anything else to retry: ");
            String c = project.sc.next();
            if(c.charAt(0)=='x') return;
            else login();
        }
    }

    public static void Admin_Menu()
    {
        project.clear_screen();
        boolean f = true;
        do
        {
            System.out.println("\t\tADMINISTRATOR MENU\n\n");
            System.out.println("\t\t1.Add product\n");
            System.out.println("\t\t2.Delete product\n");
            System.out.println("\t\t3.View List of Products\n");
            System.out.print("\t\t4.Back\n\n\n\t\tEnter choice: ");
            int choice = project.sc.nextInt();
            switch(choice)
            {
                case 1: Admin.Add_Product();
                        try
                        {
                            System.in.read();
                        }
                        catch(IOException e)
                        {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                        Admin_Menu();
                        break;

                case 2: Admin.Delete_Product();
                        try
                        {
                            System.in.read();
                        }
                        catch(IOException e)
                        {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                        Admin_Menu();
                        break;

                case 3: project.View_Products();
                        try
                        {
                            System.in.read();
                        }
                        catch(IOException e)
                        {
                            System.out.println("IO Exception");
                            e.printStackTrace();
                        }
                        Admin_Menu();
                        break;

                case 4: project.Main_Menu();
                        break;

                default : f = false;
            }
        }while(!f);
    }
}

class order implements Serializable
{
    protected String Username;
    protected double finalprice;
    protected ArrayList<Pair> orderList;

    public static final long serialVersionUID = 42L;
    
    public static ArrayList<order> OrdList = new ArrayList<order>();

    public order(Customer c)
    {
        Username = c.retUsername();
        orderList = new ArrayList<Pair>();
        finalprice = 0;
        boolean f = true;
        do
        {
            project.View_Products();
            System.out.print("\n\n\t\tEnter index of product you want to choose: ");
            int x = project.sc.nextInt();
            if(x<0 || x>=project.ProdList.size()) System.out.println("\n\t\tInvalid index!");
            else 
            {
                System.out.print("\n\t\tEnter quantity: ");
                int q = project.sc.nextInt();
                if(q>0)
                {
                    finalprice+=project.ProdList.get(x).retProductPrice()*(double)q;
                    System.out.println("\n\t\tAdded!");
                    Pair p = new Pair(project.ProdList.get(x).retProductId(),q);
                    orderList.add(p);
                }
                else System.out.println("\n\t\tInvalid quantity!");
            }
            System.out.print("\n\t\tDo you want to add more?(Y/N): ");
            String str = project.sc.next();
            f = (str.charAt(0)=='y' || str.charAt(0)=='Y');
        }while(f);
        OrdList.add(this);
    }

    public static void Get_Orders_from_File()
    {
        boolean f=true;
        try
        {
            FileInputStream fi = new FileInputStream(new File("orders.dat"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            while(f)
            {     
                order o = (order)oi.readObject();
                OrdList.add(o);
            }
            oi.close();
            fi.close();
        }
        catch(EOFException e)
        {
            f = false;
        }
        catch (IOException e) 
        {
            System.out.println("\n\t\tIO Exception");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("\n\t\tClassNotFound exception");
            e.printStackTrace();
        }
    }

    public static void Put_Orders_to_File()
    {
        try
        {
            FileOutputStream f = new FileOutputStream(new File("orders.dat"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            // Write objects to file
            for(int i=0;i<OrdList.size();++i)
            o.writeObject(OrdList.get(i));
            o.close();
            f.close();
        }
        catch (IOException e)
        {
            System.out.println("\n\t\tIO Exception");
            e.printStackTrace();
        }
    }

    public String retUsername()
    {
        return Username;
    }

    public double retfinalprice()
    {
        return finalprice;
    }

    public void PrintOrder()
    {
        //project.clear_screen();
        System.out.println("\t\tProduct   Name   Cost of 1   Quantity   Total Cost\n");
        for(int i=0;i<orderList.size();++i)
        {
            for(int j=0;j<project.ProdList.size();++j)
            {
                if(project.ProdList.get(j).retProductId()==orderList.get(i).id)
                {
                    System.out.println("\t\t" + project.ProdList.get(j).retProductName() + "\t  " + project.ProdList.get(j).retProductPrice() + "\t  " + orderList.get(i).quan + "\t  " + (double)orderList.get(i).quan*project.ProdList.get(j).retProductPrice());
                    break;
                }
            }
        }
        System.out.println("\n\n\t\tTotal Cost = " + finalprice);
    }

    public static void Prev_Orders(Customer c)
    {
        for(int i=0;i<OrdList.size();++i)
        {
            if(OrdList.get(i).Username.equals(c.retUsername()))
            {
                OrdList.get(i).PrintOrder();
                System.out.println("");
            }
        }
    }
}


public class project
{
    public static ArrayList<Product> ProdList = new ArrayList<Product>();

    static Scanner sc = new Scanner(System.in);

    public static void View_Products()
    {
        project.clear_screen();
        if(ProdList.size()==0)
        System.out.println("\n\n\t\tNo products available!!");
        else System.out.println("\n\n\t\t\tLIST OF PRODUCTS!!");
        for(int i=0;i<ProdList.size();++i)
        {
            System.out.println("\t\t" + i);
            ProdList.get(i).OutputProduct();
            System.out.println("");
        }
    }

    public static void clear_screen()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void Main_Menu()
    {
        int choice;
        boolean f = true;
        Scanner sc = new Scanner (System.in);
        do
        {
            f = true;
            project.clear_screen();
            System.out.println("\n\t\tLUXE E-COMMERCE PLATFORM - Main Menu\n\n\n");
            System.out.println("\t\t1.Customer\n");
            System.out.println("\t\t2.Admin\n");
            System.out.println("\t\t3.Exit\n\n");
            System.out.print("\t\tEnter choice: ");
            choice = sc.nextInt();
            switch(choice)
            {
                case 1: Customer.Customer_Menu();
                        break;

                case 2: Admin.login();
                        break;

                case 3: project.sc.close();
                        break;

                default : f = false;
            }
        }while(!f);
        sc.close();
    }

    public static void Get_products_from_File()
    {
        boolean f = true;
        try
        {
            FileInputStream fi = new FileInputStream(new File("products.dat"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            while(f)
            {
                Product p = (Product)oi.readObject();
                ProdList.add(p);
            }

            oi.close();
            fi.close();
        } 
        catch(EOFException e)
        {
            f = false;
        }
        catch (IOException e) 
        {
            System.out.println("IO Exception");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("ClassNotFound exception");
            e.printStackTrace();
        }
    }

    public static void Put_products_to_File()
    {
        try
        {
            FileOutputStream f = new FileOutputStream(new File("products.dat"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            for(int i=0;i<project.ProdList.size();++i)
            o.writeObject(project.ProdList.get(i));

            o.close();
            f.close();
        } 
        catch (IOException e)
        {
            System.out.println("IO Exception");
            e.printStackTrace();
        }
    }

    public static void Opening()
    {
        Customer.Get_Customers_from_File();
        order.Get_Orders_from_File();
        Get_products_from_File();
    }

    public static void Closing()
    {
        Put_products_to_File();
        Customer.Add_Customers_to_File();
        order.Put_Orders_to_File();
        project.sc.close();
    }

    public static void main(String args[])
    {
        Opening();
        Main_Menu();
        Closing();
    }
}