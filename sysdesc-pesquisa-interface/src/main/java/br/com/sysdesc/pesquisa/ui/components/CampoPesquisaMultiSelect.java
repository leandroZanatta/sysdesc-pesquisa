package br.com.sysdesc.pesquisa.ui.components;

import static br.com.sysdesc.util.resources.Resources.OPTION_VALIDACAO;
import static br.com.sysdesc.util.resources.Resources.translate;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.pesquisa.service.PesquisableService;
import br.com.sysdesc.pesquisa.ui.FrmPesquisa;
import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.classes.ListUtil;
import br.com.sysdesc.util.exception.SysDescException;
import net.miginfocom.swing.MigLayout;

public abstract class CampoPesquisaMultiSelect<T> extends JPanel {

	private static final long serialVersionUID = 1L;

	private final PesquisableService<T> genericService;
	private final Long codigoPesquisa;
	private final Long codigoUsuario;

	private JButton btPesquisa;
	private JTextField txValorPesquisa;
	private List<T> objetosPesquisados = new ArrayList<>();
	private Boolean pesquisaOk = Boolean.FALSE;

	public CampoPesquisaMultiSelect(PesquisableService<T> genericService, Long codigoPesquisa, Long codigoUsuario) {

		this.genericService = genericService;
		this.codigoPesquisa = codigoPesquisa;
		this.codigoUsuario = codigoUsuario;

		initComponents();
	}

	private void initComponents() {
		txValorPesquisa = new JTextField();
		btPesquisa = new JButton();

		btPesquisa.setIcon(ImageUtil.resize("search.png", 16, 16));
		btPesquisa.setMargin(new Insets(0, 0, 0, 0));
		btPesquisa.addActionListener((e) -> validarPesquisa());

		txValorPesquisa.setEditable(Boolean.FALSE);

		setLayout(new MigLayout("", "[grow][0.00]", "[grow]"));

		add(txValorPesquisa, "growx");
		add(btPesquisa, "east");
	}

	private void validarPesquisa() {

		if (validar()) {
			abrirPesquisa();
		}
	}

	public boolean validar() {
		return true;
	}

	private void abrirPesquisa() {

		try {
			JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);

			FrmPesquisa<T> frmPesquisa = new FrmPesquisa<>(parent, getPreFilter(), genericService, codigoPesquisa,
					codigoUsuario, Boolean.TRUE);

			frmPesquisa.setVisible(Boolean.TRUE);

			this.pesquisaOk = frmPesquisa.getOk();

			if (frmPesquisa.getOk()) {

				this.objetosPesquisados = frmPesquisa.getObjetos();

				this.carregarCampo();

				return;
			}
		} catch (SysDescException e) {
			showMessageDialog(null, e.getMensagem(), translate(OPTION_VALIDACAO), JOptionPane.WARNING_MESSAGE);
		}

		txValorPesquisa.setText("");
		this.objetosPesquisados = new ArrayList<>();

	}

	public void setValue(List<T> objeto) {

		this.objetosPesquisados = objeto;

		carregarCampo();
	}

	protected void carregarCampo() {

		if (ListUtil.isNullOrEmpty(this.objetosPesquisados)) {

			txValorPesquisa.setText("");

			return;
		}

		txValorPesquisa.setText(this.formatarValorCampo());

	}

	private String formatarValorCampo() {

		if (this.objetosPesquisados.size() == 1) {

			return this.formatarValorCampoSingle(this.objetosPesquisados.get(0));

		}

		return this.formatarValorCampoMultiple(this.objetosPesquisados);
	}

	protected abstract String formatarValorCampoMultiple(List<T> objetosPesquisados);

	protected abstract String formatarValorCampoSingle(T objeto);

	public Boolean getPesquisaOk() {
		return pesquisaOk;
	}

	public List<T> getObjetosPesquisado() {
		return objetosPesquisados;
	}

	public BooleanBuilder getPreFilter() {

		return new BooleanBuilder();
	}

	public void bloquear(Boolean editable) {
		btPesquisa.setEnabled(editable);
	}

	public void setBackgroundColor(Color color) {
		this.txValorPesquisa.setBackground(color);
	}
}
