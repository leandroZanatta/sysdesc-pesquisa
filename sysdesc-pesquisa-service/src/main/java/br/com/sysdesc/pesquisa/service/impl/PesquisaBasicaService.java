package br.com.sysdesc.pesquisa.service.impl;

import java.util.List;

import br.com.sysdesc.pesquisa.repository.dao.impl.PesquisaNormalDAO;
import br.com.sysdesc.pesquisa.repository.model.Pesquisa;
import br.com.sysdesc.util.classes.ListUtil;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemUtilConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class PesquisaBasicaService extends AbstractPesquisableServiceImpl<Pesquisa> {

	private PesquisaNormalDAO pesquisaNormalDAO;

	public PesquisaBasicaService() {
		this(new PesquisaNormalDAO());
	}

	public PesquisaBasicaService(PesquisaNormalDAO pesquisaNormalDAO) {
		super(pesquisaNormalDAO, Pesquisa::getIdPesquisa);

		this.pesquisaNormalDAO = pesquisaNormalDAO;
	}

	@Override
	public void validar(Pesquisa objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemUtilConstants.MENSAGEM_INSIRA_DESCRICAO_VALIDA);
		}

		if (LongUtil.isNullOrZero(objetoPersistir.getPaginacao())) {

			throw new SysDescException(MensagemUtilConstants.MENSAGEM_INSIRA_PAGINACAO_VALIDA);
		}

		if (LongUtil.isNullOrZero(objetoPersistir.getCodigoPesquisa())) {

			throw new SysDescException(MensagemUtilConstants.MENSAGEM_PEQUISA_VALIDA);
		}

		if (ListUtil.isNullOrEmpty(objetoPersistir.getPesquisaCampos())) {

			throw new SysDescException(MensagemUtilConstants.MENSAGEM_SELECIONE_CAMPO_PESQUISA);
		}
	}

	public List<Pesquisa> buscarRootPesquisas() {

		return pesquisaNormalDAO.listar();
	}

}
