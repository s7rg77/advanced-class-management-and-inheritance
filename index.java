package modeloBancario;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String direccion;
    private String telefono;

    public Cliente() {
        this.idCliente = 0;
        this.nombre = null;
        this.direccion = null;
        this.telefono = null;
    }

    public Cliente(int idCliente, String nombre, String direccion, 
            String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}

package modeloBancario;

public abstract class Cuenta {

    protected int numeroDeCuenta;
    protected double saldo;
    protected Cliente titular;

    public Cuenta() {
        this.numeroDeCuenta = 0;
        this.saldo = 0;
        this.titular = null;

    }

    public Cuenta(int numeroDeCuenta, double saldo, Cliente titular) {
        this.numeroDeCuenta = numeroDeCuenta;
        this.saldo = saldo;
        this.titular = titular;
    }

    public int getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setNumeroDeCuenta(int numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public void ingresar(int cantidad) {
        saldo += cantidad;
    }

    public abstract void retirar(double cantidad);

    public abstract void actualizarSaldo();

}

package modeloBancario;

public class CuentaCorriente extends Cuenta {

    protected static final double interesFijo = 0.015;

    public CuentaCorriente() {
        this.numeroDeCuenta = 0;
        this.saldo = 0;
        this.titular = null;
    }

    public CuentaCorriente(int numeroDeCuenta, double saldo, Cliente titular) {
        super(numeroDeCuenta, saldo, titular);
    }

    @Override
    public int getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    @Override
    public void setNumeroDeCuenta(int numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public Cliente getTitular() {
        return titular;
    }

    @Override
    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    @Override
    public void retirar(double cantidad) {
        if (saldo >= cantidad) {
            saldo -= cantidad;
        }
    }

    @Override
    public void actualizarSaldo() {
        saldo += saldo * interesFijo;
    }

}

package modeloBancario;

public class CuentaAhorro extends Cuenta {

    protected double interesVariable;
    protected double saldoMinimo;

    public CuentaAhorro() {
        this.numeroDeCuenta = 0;
        this.saldo = 0;
        this.titular = null;
        this.interesVariable = 0.0;
        this.saldoMinimo = 0.0;
    }

    public CuentaAhorro(int numeroDeCuenta, double saldo, Cliente titular,
            double interesVariable, double saldoMinimo) {
        super(numeroDeCuenta, saldo, titular);
        this.interesVariable = interesVariable;
        this.saldoMinimo = saldoMinimo;
    }

    public double getInteresVariable() {
        return interesVariable;
    }

    public void setInteresVariable(double interesVariable) {
        this.interesVariable = interesVariable;
    }

    public double getSaldoMinimo() {
        return saldoMinimo;
    }

    public void setSaldoMinimo(double saldoMinimo) {
        this.saldoMinimo = saldoMinimo;
    }

    @Override
    public int getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    @Override
    public void setNumeroDeCuenta(int numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public Cliente getTitular() {
        return titular;
    }

    @Override
    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    @Override
    public void retirar(double cantidad) {
        if (saldo - cantidad >= saldoMinimo) {
            saldo -= cantidad;
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public void actualizarSaldo() {
        double interes = saldo * (interesVariable / 100.0);
        saldo += interes;
    }

}

package modeloBancario;

import java.awt.HeadlessException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Metodos {

    final private String dat = "clientes.dat";
    private ArrayList<Cliente> 
            arraylist = new ArrayList<Cliente>();
    private ArrayList<CuentaCorriente> 
            arraylistcc = new ArrayList<CuentaCorriente>();
    private ArrayList<CuentaAhorro> 
            arraylistca = new ArrayList<CuentaAhorro>();

    public ArrayList<Cliente> returncliente() {
        return arraylist;
    }
    public ArrayList<CuentaCorriente> returncuentacorriente() {
        return arraylistcc;
    }
    public ArrayList<CuentaAhorro> returncuentaahorro() {
        return arraylistca;
    }

    public Metodos() {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(
                    dat));
            Cliente cliente = new Cliente();
            CuentaCorriente cc = new CuentaCorriente();
            CuentaAhorro ca = new CuentaAhorro();
            cliente.setIdCliente(Integer.parseInt(dis.readUTF()));
            while (cliente.getIdCliente() != 0) {
                cliente.setNombre(dis.readUTF());
                cliente.setDireccion(dis.readUTF());
                cliente.setTelefono(dis.readUTF());
                cc.setNumeroDeCuenta(
                        Integer.parseInt(dis.readUTF()));
                cc.setSaldo(
                        Double.parseDouble(dis.readUTF()));
                ca.setNumeroDeCuenta(
                        Integer.parseInt(dis.readUTF()));
                ca.setSaldo(
                        Double.parseDouble(dis.readUTF()));
                ca.setInteresVariable(
                        Double.parseDouble(dis.readUTF()));
                ca.setSaldoMinimo(
                        Double.parseDouble(dis.readUTF()));
                arraylist.add(cliente);
                arraylistcc.add(cc);
                arraylistca.add(ca);
                cliente = new Cliente();
                cc = new CuentaCorriente();
                ca = new CuentaAhorro();
                cliente.setIdCliente(Integer.parseInt(dis.readUTF()));
            }
            dis.close();
        } catch (IOException | NumberFormatException ex) {
        }
    }

    public void save() {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                    dat));
            for (int i = 0; i < arraylist.size(); i++) {
                dos.writeUTF(Integer.toString(arraylist.get(
                        i).getIdCliente()));
                dos.writeUTF(arraylist.get(i).getNombre());
                dos.writeUTF(arraylist.get(i).getDireccion());
                dos.writeUTF(arraylist.get(i).getTelefono());
                dos.writeUTF(Integer.toString(arraylistcc.get(i).getNumeroDeCuenta()));
                dos.writeUTF(Double.toString(arraylistcc.get(i).getSaldo()));
                dos.writeUTF(Integer.toString(arraylistca.get(i).getNumeroDeCuenta()));
                dos.writeUTF(Double.toString(arraylistca.get(i).getSaldo()));
                dos.writeUTF(Double.toString(arraylistca.get(i).getInteresVariable()));
                dos.writeUTF(Double.toString(arraylistca.get(i).getSaldoMinimo()));
            }
            dos.close();
        } catch (IOException ex) {
        }
    }

    public boolean insert(String idCliente, String nombre,
            String direccion, String telefono, String numCuentaCString,
            String numCuentaAString, String interesVarString,
            String saldoMinString) {
        try {
            if (idCliente.isEmpty() || nombre.isEmpty() || direccion.isEmpty()
                    || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "debe completar todos los campos",
                        "mensaje", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (idCliente.startsWith("0")) {
                JOptionPane.showMessageDialog(null,
                        "el idCliente no puede comenzar por cero",
                        "mensaje", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            boolean b = false;
            for (Cliente cl : arraylist) {
                if (cl.getIdCliente() == Integer.parseInt(idCliente)) {
                    b = true;
                    break;
                }
            }
            if (b) {
                JOptionPane.showMessageDialog(null,
                        "el idCliente ya ha sido introducido "
                        + "anteriormente", "mensaje",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            Cliente cl = new Cliente();
            cl.setIdCliente(Integer.parseInt(idCliente));
            cl.setNombre(nombre);
            cl.setDireccion(direccion);
            cl.setTelefono(telefono);
            arraylist.add(cl);
            CuentaCorriente cc = new CuentaCorriente();
            cc.setNumeroDeCuenta(Integer
                    .parseInt(numCuentaCString));
            cc.setSaldo(0.0);
            arraylistcc.add(cc);
            CuentaAhorro ca = new CuentaAhorro();
            ca.setNumeroDeCuenta(Integer
                    .parseInt(numCuentaAString));
            ca.setSaldo(0.0);
            ca.setInteresVariable(Double
                    .parseDouble(interesVarString));
            ca.setSaldoMinimo(Double.parseDouble(saldoMinString));
            arraylistca.add(ca);
            save();
            JOptionPane.showMessageDialog(null, "el cliente "
                    + "y sus cuentas han sido introducidos correctamente",
                    "mensaje", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "los datos "
                    + "introducidos no son válidos", "mensaje",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public String search() {
        String[] options = {"idCliente exacto", "comienza por:", "contiene:"};
        int option = JOptionPane.showOptionDialog(null,
                "seleccione una opción de búsqueda",
                "opciones", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,
                options);
        String text = "";
        boolean found = false;
        String id = JOptionPane.showInputDialog(null,
                "introduzca idCliente", "idCliente",
                JOptionPane.QUESTION_MESSAGE);
        if (id == null) {
            return "";
        }
        try {
            File file = new File("clientes.dat");
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            while (dis.available() > 0) {
                String idCliente = dis.readUTF();
                String nombre = dis.readUTF();
                String direccion = dis.readUTF();
                String telefono = dis.readUTF();
                String numeroCC = dis.readUTF();
                String saldoCC = dis.readUTF();
                String numeroCA = dis.readUTF();
                String saldoCA = dis.readUTF();
                String interesV = dis.readUTF();
                String saldoM = dis.readUTF();
                if (option == 0 && id.equals(idCliente)) {
                    text += "idCliente: " + idCliente + System.lineSeparator();
                    text += "nombre: " + nombre + System.lineSeparator();
                    text += "direccion: " + direccion + System.lineSeparator();
                    text += "telefono: " + telefono
                            + System.lineSeparator();
                    text += "numeroCuentaCorriente: " + numeroCC
                            + System.lineSeparator();
                    text += "saldoCuentaCorriente: " + saldoCC
                            + System.lineSeparator();
                    text += "numeroCuentaAhorro: " + numeroCA
                            + System.lineSeparator();
                    text += "saldoCuentaAhorro: " + saldoCA
                            + System.lineSeparator();
                    text += "interesVariable: " + interesV
                            + System.lineSeparator();
                    text += "saldoMinimo: " + saldoM;
                    text += "\n\n";
                    found = true;
                    break;
                } else if (option == 1 && idCliente
                        .startsWith(id)) {
                    text += "idCliente: " + idCliente + System.lineSeparator();
                    text += "nombre: " + nombre + System.lineSeparator();
                    text += "direccion: " + direccion + System.lineSeparator();
                    text += "telefono: " + telefono
                            + System.lineSeparator();
                    text += "numeroCuentaCorriente: " + numeroCC
                            + System.lineSeparator();
                    text += "saldoCuentaCorriente: " + saldoCC
                            + System.lineSeparator();
                    text += "numeroCuentaAhorro: " + numeroCA
                            + System.lineSeparator();
                    text += "saldoCuentaAhorro: " + saldoCA
                            + System.lineSeparator();
                    text += "interesVariable: " + interesV
                            + System.lineSeparator();
                    text += "saldoMinimo: " + saldoM;
                    text += "\n\n";
                    found = true;
                } else if (option == 2 && idCliente.contains(id)) {
                    text += "idCliente: " + idCliente + System.lineSeparator();
                    text += "nombre: " + nombre + System.lineSeparator();
                    text += "direccion: " + direccion + System.lineSeparator();
                    text += "telefono: " + telefono
                            + System.lineSeparator();
                    text += "numeroCuentaCorriente: "
                            + numeroCC + System.lineSeparator();
                    text += "saldoCuentaCorriente: "
                            + saldoCC + System.lineSeparator();
                    text += "numeroCuentaAhorro: "
                            + numeroCA + System.lineSeparator();
                    text += "saldoCuentaAhorro: "
                            + saldoCA + System.lineSeparator();
                    text += "interesVariable: "
                            + interesV + System.lineSeparator();
                    text += "saldoMinimo: " + saldoM;
                    text += "\n\n";
                    found = true;
                }
            }
            dis.close();
            fis.close();
        } catch (IOException e) {
        }
        if (!found) {
            JOptionPane.showMessageDialog(null,
                    "idCliente incorrecto",
                    "mensaje", JOptionPane.ERROR_MESSAGE);
        }
        return text;
    }

    public String list() {
        String text = "";
        text = text + "lista de clientes:" + "\n" + "\n";
        for (int i = 0; i < arraylist.size(); i++) {
            text = text + "idCliente: "
                    + arraylist.get(i).getIdCliente() + "\n";
            text = text + "nombre: "
                    + arraylist.get(i).getNombre() + "\n";
            text = text + "direccion: "
                    + arraylist.get(i).getDireccion() + "\n";
            text = text + "telefono: "
                    + arraylist.get(i).getTelefono() + "\n";
            text = text + "numeroCuentaCorriente: "
                    + arraylistcc.get(i).getNumeroDeCuenta() + "\n";
            text = text + "saldoCuentaCorriente: "
                    + arraylistcc.get(i).getSaldo() + "\n";
            text = text + "numeroCuentaAhorro: "
                    + arraylistca.get(i).getNumeroDeCuenta() + "\n";
            text = text + "saldoCuentaAhorro: "
                    + arraylistca.get(i).getSaldo() + "\n";
            text = text + "interesVariable: "
                    + arraylistca.get(i).getInteresVariable() + "\n";
            text = text + "saldoMinimo: "
                    + arraylistca.get(i).getSaldoMinimo() + "\n";
            text = text + "\n";
        }
        return text;
    }

    public boolean modify(String idClienteString, String nombre,
            String direccion, String telefono, String numCCString,
            String numCAString, String interesVString, String saldoMString) {
        if (arraylist.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "no hay datos para modificar", "mensaje",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int idCliente = Integer.parseInt(idClienteString);
            int numCC = Integer.parseInt(numCCString);
            int numCA = Integer.parseInt(numCAString);
            int interesV = Integer.parseInt(interesVString);
            int saldoM = Integer.parseInt(saldoMString);
            int mod = -1;
            for (int i = 0; i < arraylist.size(); i++) {
                if (arraylist.get(i).getIdCliente() == idCliente) {
                    mod = i;
                    break;
                }
            }
            if (mod >= 0) {
                arraylist.get(mod).setNombre(nombre);
                arraylist.get(mod).setDireccion(direccion);
                arraylist.get(mod).setTelefono(telefono);
                arraylistcc.get(mod).setNumeroDeCuenta(numCC);
                arraylistca.get(mod).setNumeroDeCuenta(numCA);
                arraylistca.get(mod).setInteresVariable(
                        interesV);
                arraylistca.get(mod).setSaldoMinimo(saldoM);
                save();
                JOptionPane.showMessageDialog(null,
                        "datos modificados correctamente", "mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null,
                        "el idCliente introducido no existe",
                        "mensaje", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "idCliente, numeroCuentaCorriente, numeroCuentaAhorro, "
                    + "interesVariable, saldoMinimo deben ser "
                    + "números enteros", "Mensaje",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public String delete() {
        Object[] options = {"borrar un cliente", "borrar todos los clientes"};
        int d = JOptionPane.showOptionDialog(null,
                "¿qué desea hacer?", "borrar clientes",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (d == JOptionPane.CLOSED_OPTION) {
            return "";
        } else if (d == 0) {
            String idClienteString = JOptionPane.showInputDialog(
                    "ingrese idCliente:");
            if (idClienteString == null) {
                return "";
            }
            int idCliente;
            try {
                idCliente = Integer.parseInt(idClienteString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "el idCliente debe ser un número entero",
                        "mensaje",
                        JOptionPane.ERROR_MESSAGE);
                return "";
            }
            int del = -1;
            for (int i = 0; i < arraylist.size(); i++) {
                if (arraylist.get(i).getIdCliente() == idCliente) {
                    del = i;
                    break;
                }
            }
            if (del >= 0) {
                arraylist.remove(del);
                arraylistcc.remove(del);
                arraylistca.remove(del);
                save();
            }
            if (del == -1) {
                JOptionPane.showMessageDialog(null, "el idCliente "
                        + idCliente + " no existe", "mensaje",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "el cliente con el idCliente " + idCliente
                        + " ha sido eliminado correctamente",
                        "mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
                return "deleted";
            }
        }
        if (d == 1) {
            int yes = JOptionPane.showConfirmDialog(null,
                    "está seguro de que desea eliminar todo?",
                    "confirmación", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (yes == JOptionPane.YES_OPTION) {
                arraylist.clear();
                arraylistcc.clear();
                arraylistca.clear();
                save();
                JOptionPane.showMessageDialog(null,
                        "todos los clientes han sido eliminados",
                        "mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
                return "deleted";
            }
        }
        return "";
    }

}

package modeloBancario;

import javax.swing.JOptionPane;

public class Entorno extends javax.swing.JFrame {

    Metodos me;
    Cliente cl;
    CuentaCorriente cc;
    CuentaAhorro ca;

    public Entorno() {
        initComponents();
        me = new Metodos();
        cl = new Cliente();
        cc = new CuentaCorriente();
        ca = new CuentaAhorro();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents

    private void initComponents() {

        btninsert = new javax.swing.JButton();
        btnsearch = new javax.swing.JButton();
        lblidCliente = new javax.swing.JLabel();
        lblnombre = new javax.swing.JLabel();
        lbldireccion = new javax.swing.JLabel();
        lbltelefono = new javax.swing.JLabel();
        txtidCliente = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txtdireccion = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtlist = new javax.swing.JTextArea();
        btnshow = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        lblproductslist = new javax.swing.JLabel();
        btndelete = new javax.swing.JButton();
        btnmodify = new javax.swing.JButton();
        btnclean = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnicc = new javax.swing.JButton();
        btnrcc = new javax.swing.JButton();
        lblncc = new javax.swing.JLabel();
        txtncc = new javax.swing.JTextField();
        lblnca = new javax.swing.JLabel();
        txtnca = new javax.swing.JTextField();
        lbliv = new javax.swing.JLabel();
        lblsm = new javax.swing.JLabel();
        txtiv = new javax.swing.JTextField();
        txtsm = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnica = new javax.swing.JButton();
        btnrca = new javax.swing.JButton();
        btnacc = new javax.swing.JButton();
        btnaca = new javax.swing.JButton();
        btnesm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Formulario de Gestión");

        btninsert.setText("Insertar");
        btninsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninsertActionPerformed(evt);
            }
        });

        btnsearch.setText("Buscar");
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        lblidCliente.setText("idCliente");

        lblnombre.setText("nombre");

        lbldireccion.setText("direccion");

        lbltelefono.setText("telefono");

        txtidCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidClienteActionPerformed(evt);
            }
        });

        txtlist.setColumns(20);
        txtlist.setRows(5);
        jScrollPane1.setViewportView(txtlist);

        btnshow.setText("Mostrar");
        btnshow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnshowActionPerformed(evt);
            }
        });

        btnexit.setText("Salir");
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });

        lblproductslist.setText("DATOS CLIENTES");

        btndelete.setText("Borrar");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnmodify.setText("Modificar");
        btnmodify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodifyActionPerformed(evt);
            }
        });

        btnclean.setText("Limpiar");
        btnclean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncleanActionPerformed(evt);
            }
        });

        jLabel1.setText("GESTIONAR DATOS CLIENTES");

        jLabel2.setText("GESTIONAR CUENTA CORRIENTE");

        btnicc.setText("ingresar");
        btnicc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btniccActionPerformed(evt);
            }
        });

        btnrcc.setText("retirar");
        btnrcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrccActionPerformed(evt);
            }
        });

        lblncc.setText("numeroCuentaCorriente");

        txtncc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnccActionPerformed(evt);
            }
        });

        lblnca.setText("numeroCuentaAhorro");

        lbliv.setText("interesVariable");

        lblsm.setText("saldoMinimo");

        jLabel4.setText("GESTIONAR CUENTA AHORRO");

        btnica.setText("ingresar");
        btnica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnicaActionPerformed(evt);
            }
        });

        btnrca.setText("retirar");
        btnrca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrcaActionPerformed(evt);
            }
        });

        btnacc.setText("actualizar (tipo interes fijo)");
        btnacc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaccActionPerformed(evt);
            }
        });

        btnaca.setText("actualizar (tipo interes variable)");
        btnaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnacaActionPerformed(evt);
            }
        });

        btnesm.setText("establecer saldo minimo");
        btnesm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnesmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblnombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbldireccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(lbltelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblidCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(txtidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbliv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblnca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblsm, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtiv, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtsm, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblncc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtncc, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblproductslist, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnmodify, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(btnclean, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnicc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnrcc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnacc, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btninsert, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(btnshow, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnica, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnrca, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnaca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnesm, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblproductslist, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbldireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbltelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblncc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtncc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblnca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbliv, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtsm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btninsert)
                        .addComponent(btnsearch)
                        .addComponent(btnshow))
                    .addComponent(btnexit))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnmodify)
                    .addComponent(btndelete)
                    .addComponent(btnclean))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnrcc)
                    .addComponent(btnicc)
                    .addComponent(btnacc))
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnica)
                    .addComponent(btnrca)
                    .addComponent(btnaca)
                    .addComponent(btnesm))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtidClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidClienteActionPerformed

    }//GEN-LAST:event_txtidClienteActionPerformed

    private void btninsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninsertActionPerformed
        boolean b = me.insert(txtidCliente.getText(),
                txtnombre.getText(), txtdireccion.getText(),
                txttelefono.getText(), txtncc.getText(),
                txtnca.getText(), txtiv.getText(),
                txtsm.getText());
        if (!b) {
            txtidCliente.setText("introduzca idCliente");
            txtnombre.setText("introduzca nombre");
            txtdireccion.setText("introduzca direccion");
            txttelefono.setText("introduzca telefono");
            txtncc.setText("introduzca numCuentaCorriente");
            txtnca.setText("introduzca numCuentaAhorro");
            txtiv.setText("introduzca interesVariable");
            txtsm.setText("introduzca saldoMinimo");
        } else {
            txtlist.setText(me.list());
            txtidCliente.setText("");
            txtnombre.setText("");
            txtdireccion.setText("");
            txttelefono.setText("");
            txtncc.setText("");
            txtnca.setText("");
            txtiv.setText("");
            txtsm.setText("");
        }
    }//GEN-LAST:event_btninsertActionPerformed

    private void btnshowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnshowActionPerformed
        txtlist.setText(me.list());
        txtidCliente.setText("");
        txtnombre.setText("");
        txtdireccion.setText("");
        txttelefono.setText("");
        txtncc.setText("");
        txtnca.setText("");
        txtiv.setText("");
        txtsm.setText("");
    }//GEN-LAST:event_btnshowActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        String sea = me.search();
        txtlist.setText(sea);
    }//GEN-LAST:event_btnsearchActionPerformed

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnexitActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        String del = me.delete();
        if ("deleted".equals(del)) {
            txtlist.setText(me.list());
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnmodifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodifyActionPerformed
        if (txtidCliente.getText().isEmpty() || txtnombre.getText().isEmpty()
                || txtdireccion.getText().isEmpty()
                || txttelefono.getText().isEmpty()
                || txtncc.getText().isEmpty() || txtnca.getText().isEmpty()
                || txtiv.getText().isEmpty() || txtsm.getText().isEmpty()) {
            txtidCliente.setText("introduzca idCliente");
            txtnombre.setText("introduzca nombre nuevo");
            txtdireccion.setText("introduzca direccion nueva");
            txttelefono.setText("introduzca telefono nuevo");
            txtncc.setText("introduzca numero CC nuevo");
            txtnca.setText("introduzca numero CA nuevo");
            txtiv.setText("introduzca interes V nuevo");
            txtsm.setText("introduzca salario M nuevo");
            JOptionPane.showMessageDialog(null,
                    "Debe completar todos los campos");
            return;
        }
        boolean mod = me.modify(txtidCliente.getText(),
                txtnombre.getText(),
                txtdireccion.getText(), txttelefono.getText(),
                txtncc.getText(),
                txtnca.getText(),
                txtiv.getText(),
                txtsm.getText());
        if (mod) {
            txtidCliente.setText("");
            txtnombre.setText("");
            txtdireccion.setText("");
            txttelefono.setText("");
            txtncc.setText("");
            txtnca.setText("");
            txtiv.setText("");
            txtsm.setText("");
            txtlist.setText(me.list());
        }
    }//GEN-LAST:event_btnmodifyActionPerformed

    private void btncleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncleanActionPerformed
        txtlist.setText("");
        txtidCliente.setText("");
        txtnombre.setText("");
        txtdireccion.setText("");
        txttelefono.setText("");
        txtncc.setText("");
        txtnca.setText("");
        txtiv.setText("");
        txtsm.setText("");
    }//GEN-LAST:event_btncleanActionPerformed

    private void txtnccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnccActionPerformed

    }//GEN-LAST:event_txtnccActionPerformed

    private void btniccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniccActionPerformed
        int numCC;
        try {
            numCC = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el numero de cuenta corriente"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un número de cuenta válido");
            return;
        }
        boolean ncc = false;
        for (CuentaCorriente cuenta : me.returncuentacorriente()) {
            if (cuenta.getNumeroDeCuenta() == numCC) {
                ncc = true;
                break;
            }
        }
        if (!ncc) {
            JOptionPane.showMessageDialog(null,
                    "el número de cuenta corriente introducido no existe");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca la cantidad a ingresar"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir una cantidad válida");
            return;
        }
        for (int i = 0; i < me.returncuentacorriente().size(); i++) {
            CuentaCorriente cuenta = me.returncuentacorriente().get(i);
            if (cuenta.getNumeroDeCuenta() == numCC) {
                cuenta.ingresar(cantidad);
                me.returncuentacorriente().set(i, cuenta);
                me.save();
                if (ncc) {
                    txtlist.setText("nombre: "
                            + me.returncliente().get(i).getNombre()
                            + "\n" + "numero cuenta corriente: "
                            + String.valueOf(me.returncuentacorriente()
                                    .get(i).getNumeroDeCuenta()) + "\n"
                            + "saldo actual cuenta corriente: "
                            + String.valueOf(me.returncuentacorriente()
                                    .get(i).getSaldo()));
                }
                JOptionPane.showMessageDialog(null,
                        "el saldo actual de la cuenta corriente " + numCC
                        + " es: " + me.returncuentacorriente()
                                .get(i).getSaldo());
                break;
            }
    }//GEN-LAST:event_btniccActionPerformed
    }

    private void btnicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnicaActionPerformed
        int numCA;
        try {
            numCA = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el número de cuenta ahorro"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un número de cuenta válido");
            return;
        }
        boolean nca = false;
        for (CuentaAhorro cuenta : me.returncuentaahorro()) {
            if (cuenta.getNumeroDeCuenta() == numCA) {
                nca = true;
                break;
            }
        }
        if (!nca) {
            JOptionPane.showMessageDialog(null,
                    "el número de cuenta ahorro introducido no existe");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca la cantidad a ingresar"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir una cantidad válida");
            return;
        }
        for (int i = 0; i < me.returncuentaahorro().size(); i++) {
            CuentaAhorro cuenta = me.returncuentaahorro().get(i);
            if (cuenta.getNumeroDeCuenta() == numCA) {
                cuenta.ingresar(cantidad);
                me.returncuentaahorro().set(i, cuenta);
                me.save();
                if (nca) {
                    txtlist.setText("nombre: "
                            + me.returncliente().get(i).getNombre() + "\n"
                            + "saldo actual cuenta ahorro: "
                            + String.valueOf(me.returncuentaahorro()
                                    .get(i).getSaldo()));
                }
                JOptionPane.showMessageDialog(null,
                        "el saldo actual de la cuenta ahorro " + numCA
                        + " es: " + me.returncuentaahorro()
                                .get(i).getSaldo());
                break;
            }
        }
    }//GEN-LAST:event_btnicaActionPerformed

    private void btnrccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrccActionPerformed
        int numCC;
        try {
            numCC = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el número de cuenta corriente"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un número de cuenta válido",
                    "error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean ncc = false;
        for (CuentaCorriente cuenta : me.returncuentacorriente()) {
            if (cuenta.getNumeroDeCuenta() == numCC) {
                ncc = true;
                break;
            }
        }
        if (!ncc) {
            JOptionPane.showMessageDialog(null,
                    "el número de cuenta corriente introducido "
                    + "no existe");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca la cantidad a retirar"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir una cantidad válida", "error",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (int i = 0; i < me.returncuentacorriente().size(); i++) {
            CuentaCorriente cuenta = me.returncuentacorriente().get(i);
            if (cuenta.getNumeroDeCuenta() == numCC) {
                if (cuenta.getSaldo() >= cantidad) {
                    cuenta.retirar(cantidad);
                    me.returncuentacorriente().set(i, cuenta);
                    me.save();
                    txtlist.setText("nombre: "
                            + me.returncliente().get(i).getNombre() + "\n"
                            + "numero cuenta corriente: "
                            + String.valueOf(me.returncuentacorriente()
                                    .get(i).getNumeroDeCuenta()) + "\n"
                            + "saldo actual cuenta corriente: "
                            + String.valueOf(me.returncuentacorriente()
                                    .get(i).getSaldo()));
                    JOptionPane.showMessageDialog(null,
                            "el saldo actual de la cuenta corriente " + numCC
                            + " es: " + me.returncuentacorriente()
                                    .get(i).getSaldo());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "saldo insuficiente", "aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
                break;
            }
        }
    }//GEN-LAST:event_btnrccActionPerformed

    private void btnrcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrcaActionPerformed
        int numCA;
        try {
            numCA = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el numero de cuenta ahorro"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un número de cuenta válido.",
                    "error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        boolean nca = false;
        for (CuentaAhorro cuenta : me.returncuentaahorro()) {
            if (cuenta.getNumeroDeCuenta() == numCA) {
                nca = true;
                break;
            }
        }
        if (!nca) {
            JOptionPane.showMessageDialog(null,
                    "el número de cuenta ahorro introducido no existe");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca la cantidad a retirar"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir una cantidad válida", "error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < me.returncuentaahorro().size(); i++) {
            CuentaAhorro cuenta = me.returncuentaahorro().get(i);
            if (cuenta.getNumeroDeCuenta() == numCA) {
                if (cuenta.getSaldo() - cantidad >= cuenta.getSaldoMinimo()) {
                    cuenta.retirar(cantidad);
                    me.returncuentaahorro().set(i, cuenta);
                    me.save();

                    txtlist.setText("nombre: "
                            + me.returncliente().get(i).getNombre() + "\n"
                            + "numero cuenta ahorro: "
                            + String.valueOf(me.returncuentaahorro()
                                    .get(i).getNumeroDeCuenta()) + "\n"
                            + "saldo actual cuenta ahorro: "
                            + String.valueOf(me.returncuentaahorro()
                                    .get(i).getSaldo()));
                    JOptionPane.showMessageDialog(null,
                            "el saldo actual de la cuenta ahorro " + numCA
                            + " es: " + me.returncuentaahorro()
                                    .get(i).getSaldo());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "saldo insuficiente", "aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
                break;
            }
        }
    }//GEN-LAST:event_btnrcaActionPerformed

    private void btnaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaccActionPerformed
        int numCC;
        try {
            numCC = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el numero de cuenta corriente"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un número de cuenta válido");
            return;
        }
        boolean ncc = false;
        for (CuentaCorriente cuenta : me.returncuentacorriente()) {
            if (cuenta.getNumeroDeCuenta() == numCC) {
                ncc = true;
                break;
            }
        }
        if (!ncc) {
            JOptionPane.showMessageDialog(null,
                    "el número de cuenta corriente introducido no existe");
            return;
        }
        for (int i = 0; i < me.returncuentacorriente().size(); i++) {
            CuentaCorriente cuenta = me.returncuentacorriente().get(i);
            if (cuenta.getNumeroDeCuenta() == numCC) {
                cuenta.actualizarSaldo();
                me.returncuentacorriente().set(i, cuenta);
                me.save();
                if (ncc) {
                    txtlist.setText("nombre: "
                            + me.returncliente().get(i).getNombre() + "\n"
                            + "numero cuenta corriente: "
                            + String.valueOf(me.returncuentacorriente()
                                    .get(i).getNumeroDeCuenta()) + "\n"
                            + "saldo actual cuenta corriente: "
                            + String.valueOf(me.returncuentacorriente()
                                    .get(i).getSaldo()));
                }
                JOptionPane.showMessageDialog(null,
                        "el saldo actual de la cuenta corriente " + numCC
                        + " es: " + me.returncuentacorriente()
                                .get(i).getSaldo());
                break;
            }
        }
    }//GEN-LAST:event_btnaccActionPerformed

    private void btnacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnacaActionPerformed
        int numCA;
        try {
            numCA = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el numero de cuenta ahorro"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un número de cuenta válido");
            return;
        }
        boolean nca = false;
        for (CuentaAhorro cuenta : me.returncuentaahorro()) {
            if (cuenta.getNumeroDeCuenta() == numCA) {
                nca = true;
                break;
            }
        }
        if (!nca) {
            JOptionPane.showMessageDialog(null,
                    "el número de cuenta ahorro introducido no existe");
            return;
        }
        double iv;
        try {
            iv = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el tipo de interés"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un tipo de interés válido",
                    "error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < me.returncuentaahorro().size(); i++) {
            CuentaAhorro cuenta = me.returncuentaahorro().get(i);
            if (cuenta.getNumeroDeCuenta() == numCA) {
                cuenta.setInteresVariable(iv);
                cuenta.actualizarSaldo();
                me.returncuentaahorro().set(i, cuenta);
                me.save();
                if (nca) {
                    txtlist.setText("nombre: "
                            + me.returncliente().get(i).getNombre() + "\n"
                            + "numero cuenta ahorro: "
                            + String.valueOf(me.returncuentaahorro()
                                    .get(i).getNumeroDeCuenta()) + "\n"
                            + "saldo actual cuenta ahorro: "
                            + String.valueOf(me.returncuentaahorro()
                                    .get(i).getSaldo()));
                }
                JOptionPane.showMessageDialog(null,
                        "el saldo actual de la cuenta ahorro " + numCA
                        + " es: " + me.returncuentaahorro()
                                .get(i).getSaldo());
                break;
            }
        }
    }//GEN-LAST:event_btnacaActionPerformed

    private void btnesmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnesmActionPerformed
        int numCA;
        try {
            numCA = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el numero de cuenta ahorro"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un número de cuenta válido");
            return;
        }
        boolean nca = false;
        for (CuentaAhorro cuenta : me.returncuentaahorro()) {
            if (cuenta.getNumeroDeCuenta() == numCA) {
                nca = true;
                break;
            }
        }
        if (!nca) {
            JOptionPane.showMessageDialog(null,
                    "el número de cuenta ahorro introducido no existe");
            return;
        }
        double sm;
        try {
            sm = Integer.parseInt(JOptionPane.showInputDialog(
                    "introduzca el saldo mínimo"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "debe introducir un saldo mínimo válido",
                    "error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < me.returncuentaahorro().size(); i++) {
            CuentaAhorro cuenta = me.returncuentaahorro().get(i);
            if (cuenta.getNumeroDeCuenta() == numCA) {
                cuenta.setSaldoMinimo(sm);
                me.returncuentaahorro().set(i, cuenta);
                me.save();
                if (nca) {
                    txtlist.setText("nombre: "
                            + me.returncliente().get(i).getNombre() + "\n"
                            + "numero cuenta ahorro: "
                            + String.valueOf(me.returncuentaahorro()
                                    .get(i).getNumeroDeCuenta()) + "\n"
                            + "saldo mínimo: "
                            + String.valueOf(me.returncuentaahorro()
                                    .get(i).getSaldoMinimo()));
                }
                JOptionPane.showMessageDialog(null,
                        "el saldo mínimo de la cuenta ahorro " + numCA
                        + " es: " + me.returncuentaahorro()
                                .get(i).getSaldoMinimo());
                break;
            }
        }
    }//GEN-LAST:event_btnesmActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Entorno().setVisible(true);
            }
        }
    );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnaca;
    private javax.swing.JButton btnacc;
    private javax.swing.JButton btnclean;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnesm;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnica;
    private javax.swing.JButton btnicc;
    private javax.swing.JButton btninsert;
    private javax.swing.JButton btnmodify;
    private javax.swing.JButton btnrca;
    private javax.swing.JButton btnrcc;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnshow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbldireccion;
    private javax.swing.JLabel lblidCliente;
    private javax.swing.JLabel lbliv;
    private javax.swing.JLabel lblnca;
    private javax.swing.JLabel lblncc;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JLabel lblproductslist;
    private javax.swing.JLabel lblsm;
    private javax.swing.JLabel lbltelefono;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtidCliente;
    private javax.swing.JTextField txtiv;
    private javax.swing.JTextArea txtlist;
    private javax.swing.JTextField txtnca;
    private javax.swing.JTextField txtncc;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtsm;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables

}