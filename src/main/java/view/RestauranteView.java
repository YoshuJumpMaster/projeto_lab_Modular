package view;

import model.*;
import controller.RestauranteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class RestauranteView {
    private JFrame frame;
    private RestauranteController controller;
    private JTextArea filaEsperaText;
    private JTextArea pedidosText;
    private JScrollPane filaEsperaScrollPane;
    private static final String FILE_PATH = "restaurante.ser";
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 18);

    public RestauranteView(RestauranteController controller) {
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Restaurante à la Classe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        JPanel panelMesas = new JPanel();
        panelMesas.setLayout(new GridLayout(3, 4));
        JButton[] btnMesas = new JButton[controller.getMesas().size()];

        for (int i = 0; i < btnMesas.length; i++) {
            Mesa mesa = controller.getMesas().get(i);
            JButton btnMesa = new JButton(getTextoMesa(mesa));
            btnMesa.setFont(DEFAULT_FONT);
            btnMesas[i] = btnMesa;
            int index = i;
            btnMesa.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (mesa.isOcupada()) {
                        showPopupMenu(e, index);
                    }
                }
            });
            panelMesas.add(btnMesa);
        }

        JPanel panelCliente = new JPanel();
        panelCliente.setLayout(new GridLayout(4, 2));

        JLabel lblNome = new JLabel("Nome do Cliente:");
        lblNome.setFont(DEFAULT_FONT);
        JTextField txtNome = new JTextField();
        txtNome.setFont(DEFAULT_FONT);
        JLabel lblPessoas = new JLabel("Número de Pessoas:");
        lblPessoas.setFont(DEFAULT_FONT);
        JTextField txtPessoas = new JTextField();
        txtPessoas.setFont(DEFAULT_FONT);
        JButton btnAdicionar = new JButton("Adicionar Cliente");
        btnAdicionar.setFont(DEFAULT_FONT);

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = txtNome.getText();
                    int numeroPessoas = Integer.parseInt(txtPessoas.getText());
                    Cliente cliente = new Cliente(nome, numeroPessoas);
                    controller.adicionarClienteNaFila(cliente);
                    atualizarFilaEspera();
                    atualizarMesas();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Número de pessoas inválido.");
                }
            }
        });

        JButton btnSalvar = new JButton("Salvar Dados");
        btnSalvar.setFont(DEFAULT_FONT);
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.salvarDados(FILE_PATH);
                    JOptionPane.showMessageDialog(frame, "Dados salvos com sucesso!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao salvar dados: " + ex.getMessage());
                }
            }
        });

        JButton btnCarregar = new JButton("Carregar Dados");
        btnCarregar.setFont(DEFAULT_FONT);
        btnCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.carregarDados(FILE_PATH);
                    atualizarFilaEspera();
                    atualizarMesas();
                    adicionarEventosMesas();  // Adicionar eventos novamente
                    JOptionPane.showMessageDialog(frame, "Dados carregados com sucesso!");
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao carregar dados: " + ex.getMessage());
                }
            }
        });

        JButton btnPedidos = new JButton("Mostrar Pedidos");
        btnPedidos.setFont(DEFAULT_FONT);
        btnPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarPedidos();
            }
        });

        JButton btnDelivery = new JButton("Adicionar Pedido de Delivery");
        btnDelivery.setFont(DEFAULT_FONT);
        btnDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = controller.getCardapio().getPratos().keySet().toArray(new String[0]);
                String item = (String) JOptionPane.showInputDialog(frame, "Escolha o item:", "Pedido de Delivery",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (item != null) {
                    String input = JOptionPane.showInputDialog("Informe a distância para a entrega (em km):");
                    try {
                        double distancia = Double.parseDouble(input);
                        Delivery delivery = new Delivery();
                        delivery.adicionarItem(item, controller.getCardapio().getPratos().get(item));
                        delivery.setTaxaEntrega(distancia * 2); // Taxa de entrega: R$ 2 por km
                        controller.adicionarPedido(delivery);
                        JOptionPane.showMessageDialog(frame, "Pedido de delivery adicionado com sucesso!");
                        atualizarPedidos();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Distância inválida.");
                    }
                }
            }
        });

        JButton btnExpandirFila = new JButton("Expandir Fila de Espera");
        btnExpandirFila.setFont(DEFAULT_FONT);
        btnExpandirFila.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expandirFilaEspera();
            }
        });

        panelCliente.add(lblNome);
        panelCliente.add(txtNome);
        panelCliente.add(lblPessoas);
        panelCliente.add(txtPessoas);
        panelCliente.add(btnAdicionar);
        panelCliente.add(btnSalvar);
        panelCliente.add(btnCarregar);
        panelCliente.add(btnPedidos);
        panelCliente.add(btnDelivery);
        panelCliente.add(btnExpandirFila);

        filaEsperaText = new JTextArea();
        filaEsperaText.setEditable(false);
        filaEsperaText.setFont(DEFAULT_FONT);
        filaEsperaText.setBorder(BorderFactory.createTitledBorder("Fila de Espera"));

        filaEsperaScrollPane = new JScrollPane(filaEsperaText);
        filaEsperaScrollPane.setPreferredSize(new Dimension(200, 300));

        pedidosText = new JTextArea();
        pedidosText.setEditable(false);
        pedidosText.setFont(DEFAULT_FONT);
        pedidosText.setBorder(BorderFactory.createTitledBorder("Pedidos"));

        frame.add(panelMesas, BorderLayout.CENTER);
        frame.add(panelCliente, BorderLayout.SOUTH);
        frame.add(filaEsperaScrollPane, BorderLayout.EAST);
        frame.add(new JScrollPane(pedidosText), BorderLayout.WEST);

        frame.setVisible(true);
    }

    private void adicionarEventosMesas() {
        Component[] components = ((JPanel) frame.getContentPane().getComponent(0)).getComponents();
        for (int i = 0; i < components.length; i++) {
            JButton btnMesa = (JButton) components[i];
            int index = i;
            btnMesa.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Mesa mesa = controller.getMesas().get(index);
                    if (mesa.isOcupada()) {
                        showPopupMenu(e, index);
                    }
                }
            });
        }
    }

    private String getTextoMesa(Mesa mesa) {
        if (mesa.isOcupada()) {
            return "Mesa " + mesa.getCapacidade() + " pessoas\n(" + mesa.getCliente().getNome() + ")";
        } else {
            return "Mesa " + mesa.getCapacidade() + " pessoas";
        }
    }

    private void atualizarFilaEspera() {
        filaEsperaText.setText("");
        for (Cliente cliente : controller.getFilaEspera()) {
            filaEsperaText.append("Cliente: " + cliente.getNome() + " - Pessoas: " + cliente.getNumeroPessoas() + "\n");
        }
    }

    private void expandirFilaEspera() {
        Dimension newSize = filaEsperaScrollPane.getPreferredSize();
        if (newSize.width == 200) {
            newSize = new Dimension(400, 600); 
        } else {
            newSize = new Dimension(200, 300); 
        }
        filaEsperaScrollPane.setPreferredSize(newSize);
        frame.revalidate();
    }

    private void atualizarPedidos() {
        pedidosText.setText("");
        for (Pedido pedido : controller.getPedidos()) {
            pedidosText.append("Pedido: " + pedido.getItens() + " - Total: " + pedido.calcularValorComServico() + "\n");
        }
    }

    private void atualizarMesas() {
        Component[] components = ((JPanel) frame.getContentPane().getComponent(0)).getComponents();
        for (int i = 0; i < components.length; i++) {
            JButton btnMesa = (JButton) components[i];
            Mesa mesa = controller.getMesas().get(i);
            btnMesa.setEnabled(true);
            btnMesa.setText(getTextoMesa(mesa));
        }
    }

    private void showPopupMenu(MouseEvent e, int index) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Adicionar Pedido");
        JMenuItem requestBillItem = new JMenuItem("Solicitar Conta");
        JMenuItem freeTableItem = new JMenuItem("Desocupar Mesa");

        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String[] options = controller.getCardapio().getPratos().keySet().toArray(new String[0]);
                String item = (String) JOptionPane.showInputDialog(frame, "Escolha o item:", "Adicionar Pedido",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (item != null) {
                    controller.adicionarPedidoAMesa(index, item);
                    JOptionPane.showMessageDialog(frame, "Pedido adicionado à mesa com sucesso!");
                    atualizarPedidos();
                }
            }
        });

        requestBillItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                double total = controller.calcularContaMesa(index);
                Mesa mesa = controller.getMesas().get(index);
                Cliente cliente = mesa.getCliente();
                double totalPorPessoa = total / cliente.getNumeroPessoas();
                JOptionPane.showMessageDialog(frame, "Total: R$" + total + "\nTotal por pessoa: R$" + totalPorPessoa);
            }
        });

        freeTableItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Mesa mesa = controller.getMesas().get(index);
                mesa.setCliente(null);
                atualizarMesas();
            }
        });

        popupMenu.add(addItem);
        popupMenu.add(requestBillItem);
        popupMenu.add(freeTableItem);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}
