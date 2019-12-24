package br.ufscar.dc.latosensu.aplicacaofinanceira.service;

import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.NotFoundException;
import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.NotUniqueException;
import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.ValidationException;
import br.ufscar.dc.latosensu.aplicacaofinanceira.model.Banco;
import br.ufscar.dc.latosensu.aplicacaofinanceira.repository.BancoRepository;
import br.ufscar.dc.latosensu.aplicacaofinanceira.validation.ValidationUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@Transactional
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;
    
    @Autowired
    private MessageSource messageSource;

    public void delete(long id) throws NotFoundException {
        Banco banco = bancoRepository.findById(id);

        if (banco == null) {
            throw new NotFoundException(messageSource.getMessage("bancoNaoEncontrado", null, null));
        }
        
        bancoRepository.delete(banco);
    }

    public List<Banco> findAll() {
        return bancoRepository.findAll(Sort.by("nome"));
    }    

    public Banco findById(long id) throws NotFoundException {
        Banco banco = bancoRepository.findById(id);

        if (banco == null) {
            throw new NotFoundException(messageSource.getMessage("bancoNaoEncontrado", null, null));
        }        
        
        return banco;
    }

    public Banco save(Banco banco, BindingResult bindingResult) throws NotUniqueException, ValidationException {
        new ValidationUtil().validate(bindingResult);               
        
        if (!isNumberUnique(banco.getNumero())) {
            throw new NotUniqueException(messageSource.getMessage("bancoNumeroDeveSerUnico", null, null));
        }

        return bancoRepository.save(banco);
    }

    public Banco update(long id, Banco banco, BindingResult bindingResult) throws NotFoundException, NotUniqueException, ValidationException {
        new ValidationUtil().validate(bindingResult);   
        
        Banco bancoToUpdate = findById(id);

        if (bancoToUpdate == null) {
            throw new NotFoundException(messageSource.getMessage("bancoNaoEncontrado", null, null));
        }
        
        if (!isNumberUnique(banco.getNumero(), bancoToUpdate.getId())) {
            throw new NotUniqueException(messageSource.getMessage("bancoNumeroDeveSerUnico", null, null));
        }

        bancoToUpdate.setNumero(banco.getNumero());
        bancoToUpdate.setCnpj(banco.getCnpj());
        bancoToUpdate.setNome(banco.getNome());

        return bancoRepository.save(bancoToUpdate);
    }

    private boolean isNumberUnique(Integer numero) {
        Banco banco = bancoRepository.findByNumero(numero);
        
        return banco == null ? true : false;
    }
    
    private boolean isNumberUnique(Integer numero, Long id) {
        Banco banco = bancoRepository.findByNumeroAndDifferentId(numero, id);
        
        return banco == null ? true : false;
    }
}
