package br.com.sysdesc.pesquisa.ui;

import static br.com.sysdesc.util.constants.MensagemUtilConstants.MENSAGEM_PESQUISA_NAO_CONFIGURADA;
import static br.com.sysdesc.util.constants.MensagemUtilConstants.MENSAGEM_SELECIONE_APENAS_UM_REGISTRO;
import static br.com.sysdesc.util.constants.MensagemUtilConstants.MENSAGEM_SELECIONE_UM_REGISTRO;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_VERIFICACAO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.pesquisa.repository.dao.impl.PesquisaDAO;
import br.com.sysdesc.pesquisa.repository.dao.impl.PesquisaPadraoDAO;
import br.com.sysdesc.pesquisa.repository.model.Pesquisa;
import br.com.sysdesc.pesquisa.repository.model.PesquisaCampo;
import br.com.sysdesc.pesquisa.repository.model.PesquisaPadrao;
import br.com.sysdesc.pesquisa.service.PesquisableService;
import br.com.sysdesc.pesquisa.service.builders.ReportBuilder;
import br.com.sysdesc.pesquisa.ui.components.ReportViewer;
import br.com.sysdesc.pesquisa.ui.models.GenericTableModel;
import br.com.sysdesc.pesquisa.util.enumeradores.TipoTamanhoEnum;
import br.com.sysdesc.util.classes.ContadorUtil;
import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.exception.SysDescException;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JasperPrint;

public class FrmPesquisa<T> extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final String STR_REGISTROS = "Exibindo %s de %s registros";
	private NumberFormat numberFormat = NumberFormat.getNumberInstance();
	private JTable table;
	private JButton btConfigurar;
	private JPanel panelBotton;
	private JPanel panelHeader;
	private JScrollPane scrollPane;
	private JTextFieldMaiusculo txPesquisa;
	private JButton btImprimir;
	private JButton btnPesquisar;
	private JButton btnSelecionar;
	private JCheckBox chckbxContm;

	private Boolean ok = Boolean.FALSE;
	private List<T> objeto = new ArrayList<>();
	private PesquisaDAO pesquisaDAO = new PesquisaDAO();
	private PesquisaPadraoDAO pesquisaPadraoDAO = new PesquisaPadraoDAO();
	private Pesquisa pesquisaExibir;
	private GenericTableModel<T> genericTableModel;
	private Integer rows = 0;
	private Long numeroregistros = 0L;
	private JLabel lbRegistros;
	private final PesquisableService<T> pesquisableService;
	private final Long codigoPesquisa;
	private final Long codigoUsuario;
	private final Boolean multiselect;
	private final BooleanBuilder preFilter;

	public FrmPesquisa(JFrame parent, BooleanBuilder preFilter, PesquisableService<T> pesquisableService,
			Long codigoPesquisa, Long codigoUsuario) {
		this(parent, preFilter, pesquisableService, codigoPesquisa, codigoUsuario, Boolean.FALSE);
	}

	public FrmPesquisa(JFrame parent, BooleanBuilder preFilter, PesquisableService<T> pesquisableService,
			Long codigoPesquisa, Long codigoUsuario, Boolean multiselect) {
		super(parent, Boolean.TRUE);

		this.codigoPesquisa = codigoPesquisa;
		this.codigoUsuario = codigoUsuario;
		this.preFilter = preFilter;
		this.pesquisableService = pesquisableService;
		this.multiselect = multiselect;

		this.initComponents();
		this.buscarPesquisa();
		this.carregarCampos();
		this.iniciarPesquisa();
	}

	private void initComponents() {
		setSize(800, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panelBotton = new JPanel();
		txPesquisa = new JTextFieldMaiusculo();
		btnPesquisar = new JButton("Pesquisar");
		btnSelecionar = new JButton("Selecionar");
		lbRegistros = new JLabel("");
		btConfigurar = new JButton("");
		btImprimir = new JButton("");
		panelHeader = new JPanel();
		chckbxContm = new JCheckBox("Contém");
		scrollPane = new JScrollPane();
		table = new JTable();

		panelBotton.setLayout(new MigLayout("", "[grow][][]", "[14px]"));
		panelHeader.setLayout(new MigLayout("", "[63px,left][86px,grow][79px][81px]", "[23px]"));

		btConfigurar.setIcon(ImageUtil.resize("config.png", 20, 20));

		txPesquisa.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					iniciarPesquisa();
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					selecionarRegistro();
				}

			}
		});

		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					selecionarRegistro();
				}
			}
		});

		btnSelecionar.addActionListener((e) -> selecionarRegistro());

		scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {

			int extent = scrollPane.getVerticalScrollBar().getModel().getExtent();
			int maximum = scrollPane.getVerticalScrollBar().getMaximum();
			int vPos = scrollPane.getVerticalScrollBar().getValue();

			if (vPos + extent >= maximum) {
				pesquisar();
			}
		});

		btImprimir.setIcon(ImageUtil.resize("print.png", 20, 20));
		btImprimir.addActionListener(e -> imprimir());

		scrollPane.setViewportView(table);

		panelBotton.add(lbRegistros, "flowx,cell 0 0,alignx left,aligny center");
		panelBotton.add(btConfigurar, "cell 1 0");
		panelBotton.add(btImprimir, "cell 2 0,grow");

		panelHeader.add(chckbxContm, "cell 0 0,alignx left,aligny top");
		panelHeader.add(txPesquisa, "cell 1 0,growx,aligny center");
		panelHeader.add(btnPesquisar, "cell 2 0,alignx left,aligny top");
		panelHeader.add(btnSelecionar, "cell 3 0,alignx left,aligny top");

		getContentPane().add(panelBotton, BorderLayout.SOUTH);
		getContentPane().add(panelHeader, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

	}

	private void imprimir() {

		try {

			List<T> data = pesquisableService.pesquisarTodos(chckbxContm.isSelected(), txPesquisa.getText(),
					this.preFilter, pesquisaExibir);

			ReportBuilder<T> reportBuilder = new ReportBuilder<>(this.pesquisaExibir, data);

			JasperPrint jasperPrint = reportBuilder.build();

			new ReportViewer(this, jasperPrint).setVisible(Boolean.TRUE);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "OCORREU UM ERRO AO GERAR RELATÓRIO\n" + e.getMessage());
		}
	}

	private void selecionarRegistro() {

		if (table.getSelectedRowCount() == 0) {
			JOptionPane.showMessageDialog(getParent(), translate(MENSAGEM_SELECIONE_UM_REGISTRO),
					translate(FRMLOGIN_MSG_VERIFICACAO), JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!this.multiselect && table.getSelectedRowCount() != 1) {
			JOptionPane.showMessageDialog(getParent(), MENSAGEM_SELECIONE_APENAS_UM_REGISTRO,
					translate(FRMLOGIN_MSG_VERIFICACAO), JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (int row : table.getSelectedRows()) {
			this.objeto.add(genericTableModel.getRow(row));
		}

		this.ok = Boolean.TRUE;

		dispose();

	}

	private void iniciarPesquisa() {

		this.rows = 0;

		genericTableModel.removeAll();

		numeroregistros = pesquisableService.count(chckbxContm.isSelected(), txPesquisa.getText(), this.preFilter,
				pesquisaExibir);

		this.pesquisar();

	}

	private void pesquisar() {

		List<T> valores = pesquisableService.pesquisar(chckbxContm.isSelected(), txPesquisa.getText(), this.preFilter,
				pesquisaExibir, rows);

		genericTableModel.addRows(valores);

		rows += valores.size();

		lbRegistros
				.setText(String.format(STR_REGISTROS, numberFormat.format(rows), numberFormat.format(numeroregistros)));

	}

	private void buscarPesquisa() {

		List<Pesquisa> pesquisa = pesquisaDAO.buscarPesquisaPorUsuario(this.codigoUsuario, this.codigoPesquisa);

		btConfigurar.setVisible(pesquisa.size() != 1);

		selecionarPesquisa(pesquisa);

	}

	private void selecionarPesquisa(List<Pesquisa> pesquisa) {

		if (pesquisa.isEmpty()) {
			throw new SysDescException(MENSAGEM_PESQUISA_NAO_CONFIGURADA);
		}

		if (pesquisa.size() == 1) {
			this.pesquisaExibir = pesquisa.get(0);

			return;
		}

		JPopupMenu popup = new JPopupMenu();

		ContadorUtil contadorUtil = new ContadorUtil();

		pesquisa.forEach(p -> {

			contadorUtil.next();

			JMenuItem jMenuItem = new JMenuItem(p.getDescricao());

			jMenuItem.addActionListener(l -> salvarPesquisaPadrao(p));

			popup.add(jMenuItem);
		});

		Integer height = contadorUtil.getValue().intValue() * 25;

		popup.setPreferredSize(new Dimension(200, height));

		btConfigurar.addActionListener(l -> {

			Point p = btConfigurar.getLocationOnScreen();

			popup.show(this, 0, 0);

			popup.setLocation(p.x - 190, p.y - 50);

		});

		this.pesquisaExibir = pesquisa.stream().filter(x -> x.getPesquisaPadrao() != null).findFirst()
				.orElse(pesquisa.get(0));
	}

	private void salvarPesquisaPadrao(Pesquisa pesquisa) {

		pesquisaPadraoDAO.excluirPesquisaUsuario(pesquisa.getCodigoPesquisa(), codigoUsuario);

		PesquisaPadrao pesquisaPadrao = new PesquisaPadrao();
		pesquisaPadrao.setCodigoPesquisa(pesquisa.getIdPesquisa());
		pesquisaPadrao.setCodigoUsuario(codigoUsuario);

		pesquisaPadraoDAO.salvar(pesquisaPadrao);

		this.pesquisaExibir = pesquisa;

		this.carregarCampos();
		this.iniciarPesquisa();
	}

	private void carregarCampos() {

		chckbxContm.setSelected(Boolean.TRUE);

		List<PesquisaCampo> campos = pesquisaExibir.getPesquisaCampos();

		this.genericTableModel = new GenericTableModel<>(campos);

		this.table.setModel(genericTableModel);

		Long tamanho = 800L;

		Long tamanhoFixo = campos.stream().filter(x -> x.getFlagTipoTamanho().equals(1L))
				.mapToLong(PesquisaCampo::getFlagTipoTamanho).sum();

		Long tamanhoflex = campos.stream().filter(x -> x.getFlagTipoTamanho().equals(2L))
				.mapToLong(PesquisaCampo::getFlagTipoTamanho).sum();

		Long conversaoflex = 0L;

		if (tamanhoflex > 0) {

			conversaoflex = (tamanho - tamanhoFixo) / tamanhoflex;
		}

		ContadorUtil coluna = new ContadorUtil();

		for (PesquisaCampo campo : pesquisaExibir.getPesquisaCampos()) {

			Long tamanhoColuna = 0L;

			if (TipoTamanhoEnum.FLEX.getCodigo().equals(campo.getFlagTipoTamanho())) {

				tamanhoColuna = campo.getNumeroTamanho() * conversaoflex;
			} else {
				tamanhoColuna = campo.getNumeroTamanho();
			}

			table.getColumnModel().getColumn(coluna.getValue().intValue()).setPreferredWidth(tamanhoColuna.intValue());

			coluna.next();
		}
	}

	public boolean getOk() {
		return ok;
	}

	public T getObjeto() {

		return objeto.get(0);
	}

	public List<T> getObjetos() {

		return objeto;
	}

}
