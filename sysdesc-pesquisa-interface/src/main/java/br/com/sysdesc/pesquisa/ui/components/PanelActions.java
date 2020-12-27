package br.com.sysdesc.pesquisa.ui.components;

import static br.com.sysdesc.util.resources.Resources.OPTION_VALIDACAO;
import static br.com.sysdesc.util.resources.Resources.translate;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.listeners.ButtonActionListener;
import br.com.sysdesc.components.listeners.NewListener;
import br.com.sysdesc.components.listeners.SaveListener;
import br.com.sysdesc.pesquisa.components.buttonactions.AbstractButtonAction;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonAction;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionAvancar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionBuscar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionCancelar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionEditar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionNovo;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionPrimeiro;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionRetroceder;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionSalvar;
import br.com.sysdesc.pesquisa.components.buttonactions.ButtonActionUltimo;
import br.com.sysdesc.pesquisa.service.PesquisableService;
import br.com.sysdesc.pesquisa.ui.FrmPesquisa;
import br.com.sysdesc.util.classes.ClassTypeUtil;
import br.com.sysdesc.util.classes.ContadorUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.exception.SysDescException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class PanelActions<T> extends AbstractButtonAction {

	private static final long serialVersionUID = 1L;

	private PesquisableService<T> genericService;
	protected ButtonActionSalvar btSalvar;
	protected ButtonActionEditar btEditar;
	protected ButtonActionNovo btNovo;
	protected ButtonActionBuscar btBuscar;
	protected ButtonActionCancelar btCancelar;

	protected ButtonActionAvancar btAvancar;
	protected ButtonActionRetroceder btRetroceder;
	protected ButtonActionPrimeiro btPrimeiro;
	protected ButtonActionUltimo btUltimo;

	private final JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
	private final boolean pageable;
	protected T objetoPesquisa;
	protected List<ButtonAction> actions = new ArrayList<>();
	private ActionMap actionMap = new ActionMap();
	private InputMap inputMap;
	protected EventListenerList buttonListener = new EventListenerList();
	protected EventListenerList saveListener = new EventListenerList();
	protected EventListenerList newListener = new EventListenerList();
	private BooleanBuilder preFilter = new BooleanBuilder();

	private final Long codigoPesquisa;

	public PanelActions(AbstractInternalFrame internalFrame, PesquisableService<T> genericService, Long codigoPesquisa, ButtonAction... actions) {

		this(internalFrame, genericService, codigoPesquisa, Boolean.TRUE, actions);
	}

	public PanelActions(AbstractInternalFrame internalFrame, PesquisableService<T> genericService, Long codigoPesquisa, Boolean pageable,
			ButtonAction... actions) {

		super(internalFrame);

		this.codigoPesquisa = codigoPesquisa;
		this.pageable = pageable;
		this.genericService = genericService;

		initComponents(actions);
	}

	private void initComponents(ButtonAction... actions) {

		inputMap = internalFrame.getRootPane().getInputMap(WHEN_IN_FOCUSED_WINDOW);

		btSalvar = new ButtonActionSalvar();
		btEditar = new ButtonActionEditar();
		btNovo = new ButtonActionNovo();
		btBuscar = new ButtonActionBuscar();
		btCancelar = new ButtonActionCancelar();

		final PanelActions<T> painel = this;

		Action actionSalvar = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				saveEvent();
			}
		};

		Action actionEditar = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				editEvent();
			}
		};

		Action actionNovo = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				newEvent(painel);
			}
		};

		Action actionLocalizar = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				searchEvent();
			}
		};

		Action actionCancelar = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				internalFrame.dispose();
			}
		};

		if (pageable) {

			btAvancar = new ButtonActionAvancar();
			btRetroceder = new ButtonActionRetroceder();
			btUltimo = new ButtonActionUltimo();
			btPrimeiro = new ButtonActionPrimeiro();

			Action actionAvancar = new AbstractAction() {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					avancarEvent();
				}
			};

			Action actionRetroceder = new AbstractAction() {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					retrocederEvent();
				}
			};

			Action actionUltimo = new AbstractAction() {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					ultimoEvent();
				}
			};

			Action actionPrimeiro = new AbstractAction() {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					primeiroEvent();
				}
			};

			registrarEvento(btAvancar, actionAvancar);
			registrarEvento(btRetroceder, actionRetroceder);
			registrarEvento(btUltimo, actionUltimo);
			registrarEvento(btPrimeiro, actionPrimeiro);

		}
		registrarEventosBotoesPagina();

		registrarEvento(btSalvar, actionSalvar);
		registrarEvento(btEditar, actionEditar);
		registrarEvento(btNovo, actionNovo);
		registrarEvento(btBuscar, actionLocalizar);
		registrarEvento(btCancelar, actionCancelar);

		posicionarEAdicionarBotoes(Arrays.asList(actions));

		internalFrame.getRootPane().setActionMap(actionMap);

		registrarCampos();

		limpar();

		setEditable(Boolean.FALSE);
		bloquear();

		fireButtonListener(ButtonActionListener::startEvent);
	}

	protected void registrarEventosBotoesPagina() {
	}

	protected void registrarEvento(ButtonAction buttonAction, Action action) {

		buttonAction.addActionListener(action);

		this.addButtonListener(buttonAction);

		if (!StringUtil.isNullOrEmpty(buttonAction.getMapName())) {

			inputMap.put(buttonAction.getKeyStroke(), buttonAction.getMapName());

			actionMap.put(buttonAction.getMapName(), action);

		}
	}

	private void searchEvent() {

		boolean pesquisou = pesquisar();

		if (pesquisou) {

			limpar();

			carregarObjeto(objetoPesquisa);

			fireButtonListener(ButtonActionListener::searchEvent);
		}
	}

	public void carregarObjetoPesquisado(T objeto) {

		limpar();

		setEditable(Boolean.FALSE);

		bloquear();

		objetoPesquisa = objeto;

		carregarObjeto(objetoPesquisa);

		fireButtonListener(ButtonActionListener::searchEvent);

	}

	private void editEvent() {

		setEditable(Boolean.TRUE);

		bloquear();

		fireButtonListener(ButtonActionListener::editEvent);
	}

	private void posicionarEAdicionarBotoes(List<ButtonAction> actions) {

		this.actions.addAll(actions);
		this.actions.add(btSalvar);
		this.actions.add(btEditar);
		this.actions.add(btNovo);
		this.actions.add(btBuscar);
		this.actions.add(btCancelar);

		if (pageable) {

			this.actions.add(btAvancar);
			this.actions.add(btRetroceder);
			this.actions.add(btUltimo);
			this.actions.add(btPrimeiro);
		}

		posicionarBotoes();

		this.actions.stream().sorted(Comparator.comparing(ButtonAction::getOrdem)).forEach(this::add);
	}

	private void saveEvent() {

		try {

			if (preencherObjeto(objetoPesquisa)) {

				genericService.validar(objetoPesquisa);

				genericService.salvar(objetoPesquisa);

				setEditable(Boolean.FALSE);

				bloquear();

				fireSaveEvent(objetoPesquisa);

				fireButtonListener(ButtonActionListener::saveEvent);
			}

		} catch (SysDescException sysDescException) {

			genericService.invalidarObjeto();

			showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
		}

	}

	protected void posicionarBotoes() {

		ContadorUtil contadorUtil = new ContadorUtil();

		posicionarBotao(contadorUtil, btPrimeiro, pageable);
		posicionarBotao(contadorUtil, btRetroceder, pageable);

		posicionarBotao(contadorUtil, btSalvar, Boolean.TRUE);
		posicionarBotao(contadorUtil, btEditar, Boolean.TRUE);
		posicionarBotao(contadorUtil, btNovo, Boolean.TRUE);
		posicionarBotao(contadorUtil, btBuscar, Boolean.TRUE);
		posicionarBotao(contadorUtil, btCancelar, Boolean.TRUE);

		posicionarBotao(contadorUtil, btAvancar, pageable);
		posicionarBotao(contadorUtil, btUltimo, pageable);

	}

	protected void posicionarBotao(ContadorUtil contadorUtil, ButtonAction buttonAction, boolean adicionar) {

		if (adicionar) {
			buttonAction.setOrdem(contadorUtil.next().intValue());
		} else {
			buttonAction.setVisible(false);
		}
	}

	private void retrocederEvent() {

		try {

			objetoPesquisa = genericService.previows(getValueId());

			limpar();

			setEditable(Boolean.FALSE);

			bloquear();

			carregarObjeto(objetoPesquisa);

			fireButtonListener(ButtonActionListener::searchEvent);

		} catch (SysDescException sysDescException) {

			showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
		}

	}

	private Long getValueId() {

		Long valor = null;

		if (objetoPesquisa != null) {
			valor = genericService.getId().apply(objetoPesquisa);
		}

		return valor;
	}

	private void ultimoEvent() {

		try {
			objetoPesquisa = genericService.last();

			limpar();

			setEditable(Boolean.FALSE);

			bloquear();

			carregarObjeto(objetoPesquisa);

			fireButtonListener(ButtonActionListener::searchEvent);

		} catch (SysDescException sysDescException) {

			showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
		}
	}

	private void primeiroEvent() {

		try {

			objetoPesquisa = genericService.first();

			limpar();

			setEditable(Boolean.FALSE);

			bloquear();

			carregarObjeto(objetoPesquisa);

			fireButtonListener(ButtonActionListener::searchEvent);

		} catch (SysDescException sysDescException) {

			showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
		}

	}

	private void avancarEvent() {

		try {

			objetoPesquisa = genericService.next(getValueId());

			limpar();

			setEditable(Boolean.FALSE);

			bloquear();

			carregarObjeto(objetoPesquisa);

			fireButtonListener(ButtonActionListener::searchEvent);

		} catch (SysDescException sysDescException) {

			showMessageDialog(parent, sysDescException.getMensagem(), translate(OPTION_VALIDACAO), WARNING_MESSAGE);
		}

	}

	public boolean pesquisar() {

		try {

			FrmPesquisa<T> frmPesquisa = new FrmPesquisa<>(parent, getPreFilter(), this.genericService, codigoPesquisa,
					this.internalFrame.getCodigoUsuario());

			frmPesquisa.setVisible(Boolean.TRUE);

			if (frmPesquisa.getOk()) {

				this.objetoPesquisa = frmPesquisa.getObjeto();

				limpar();

				setEditable(Boolean.FALSE);

				bloquear();

				carregarObjeto(objetoPesquisa);
			}

			return frmPesquisa.getOk();

		} catch (SysDescException e) {

			showMessageDialog(parent, e.getMensagem(), translate(OPTION_VALIDACAO), JOptionPane.WARNING_MESSAGE);

			return Boolean.FALSE;
		}
	}

	public BooleanBuilder getPreFilter() {

		return this.preFilter;
	}

	public void setPreFilter(BooleanBuilder preFilter) {

		this.preFilter = preFilter;
	}

	public void addButtonListener(ButtonActionListener buttonActionListener) {

		buttonListener.add(ButtonActionListener.class, buttonActionListener);
	}

	public void addNewListener(NewListener<T> newListener) {

		this.newListener.add(NewListener.class, newListener);
	}

	public void addSaveListener(SaveListener<T> saveListener) {

		this.saveListener.add(SaveListener.class, saveListener);
	}

	@SuppressWarnings("unchecked")
	private void newEvent(PanelActions<T> painel) {

		try {

			objetoPesquisa = (T) ClassTypeUtil.getGenericType(painel.getClass()).getDeclaredConstructor().newInstance();

			limpar();

			setEditable(Boolean.TRUE);

			bloquear();

			fireNewEvent(objetoPesquisa);

			fireButtonListener(ButtonActionListener::newEvent);

		} catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e1) {

			log.error("Erro ao instanciar objeto", e1);
		}

	}

	@SuppressWarnings("unchecked")
	public void fireSaveEvent(T evt) {
		Object[] listeners = saveListener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == SaveListener.class) {

				((SaveListener<T>) listeners[i + 1]).saveEvent(evt);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void fireNewEvent(T evt) {
		Object[] listeners = newListener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == NewListener.class) {

				((NewListener<T>) listeners[i + 1]).newEvent(evt);
			}
		}
	}

	private void fireButtonListener(Consumer<ButtonActionListener> consumer) {

		Object[] listeners = buttonListener.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == ButtonActionListener.class) {

				consumer.accept(((ButtonActionListener) listeners[i + 1]));
			}
		}
	}

	public T getObjetoPesquisa() {
		return objetoPesquisa;
	}

	protected abstract void carregarObjeto(T objetoPesquisa);

	public abstract boolean preencherObjeto(T objetoPesquisa);

}
