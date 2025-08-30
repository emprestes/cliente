package domain.model;

import domain.exception.MunicipioException;
import domain.exception.MunicipioInvalidoException;

import static java.util.Optional.ofNullable;

/**
 * Representa um município (cidade) identificado por nome e UF.
 */
public class Municipio extends Entidade<Integer, MunicipioException> implements Comparable<Municipio> {

    /**
     * Nome do município.
     */
    private CharSequence nome;
    /** Unidade Federativa (UF) do município. */
    private UFVO uf;

    /**
     * Cria um município com nome vazio e UF não selecionada.
     */
    public Municipio() {
        this("");
    }

    /**
     * Cria um município com o nome informado e UF não selecionada.
     *
     * @param nome nome do município
     */
    public Municipio(CharSequence nome) {
        this(nome, UFVO.SELECIONE);
    }

    /**
     * Cria um município a partir de uma UF informada como texto.
     *
     * @param nome nome do município
     * @param uf   UF em texto (sigla)
     */
    public Municipio(CharSequence nome, CharSequence uf) {
        this(nome, UFVO.valueOf(uf));
    }

    /**
     * Cria um município com nome e UF informados.
     *
     * @param nome nome do município
     * @param uf   UF do município
     */
    public Municipio(CharSequence nome, UFVO uf) {
        super();

        this.nome = nome;
        this.uf = uf;
    }

    /**
     * Obtém o nome do município.
     *
     * @return nome atual
     */
    public CharSequence getNome() {
        return nome;
    }

    /**
     * Define o nome do município, aplicando trim quando necessário.
     *
     * @param nome novo nome
     */
    public void setNome(CharSequence nome) {
        this.nome = nome != null && nome.length() > 0 ? nome.toString().trim() : nome;
    }

    /**
     * Obtém a UF do município.
     *
     * @return UF atual
     */
    public UFVO getUf() {
        return uf;
    }

    /**
     * Define a UF do município a partir de um texto.
     *
     * @param uf UF em texto (sigla)
     */
    public void setUf(CharSequence uf) {
        setUf(UFVO.valueOf(uf));
    }

    /**
     * Define a UF do município.
     *
     * @param uf nova UF
     */
    public void setUf(UFVO uf) {
        this.uf = uf;
    }

    /**
     * Valida o nome do município segundo as regras de negócio.
     *
     * @throws MunicipioInvalidoException quando o nome é nulo, vazio ou inválido
     */
    public void validarNome() throws MunicipioInvalidoException {
        if (nome == null) {
            throw new MunicipioInvalidoException("Nome nulo do município!");
        }

        if (nome.length() == 0) {
            throw new MunicipioInvalidoException("Por favor, informe o nome do município!");
        }

        if (!String.valueOf(nome).matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$")) {
            throw new MunicipioInvalidoException("Nome do município inválido!");
        }
    }

    /**
     * Valida a UF do município.
     *
     * @throws MunicipioInvalidoException quando a UF é nula ou não selecionada
     */
    public void validarUf() throws MunicipioInvalidoException {
        if (uf == null) {
            throw new MunicipioInvalidoException("UF nula do município!");
        }

        if (uf.isNotSelecionado()) {
            throw new MunicipioInvalidoException("Por favor, selecione a UF do município!");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void validar() throws MunicipioInvalidoException {
        validarUf();
        validarNome();
    }

    /**
     * Ordena municípios por UF e, em seguida, por nome (case-insensitive).
     */
    @Override
    public int compareTo(Municipio o) {
        int comp = uf.compareTo(o.uf);

        if (comp == 0) {
            comp = String.valueOf(nome).compareToIgnoreCase(String.valueOf(o.nome));
        }

        return comp;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((uf == null) ? 0 : uf.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Municipio other)) {
            return false;
        }
        if (nome == null) {
            if (other.nome != null) {
                return false;
            }
        } else if (!nome.equals(other.nome)) {
            return false;
        }
        return uf == other.uf;
    }

    /** {@inheritDoc} */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Municipio m = ofNullable(super.clone())
                .filter(o -> o instanceof Municipio)
                .map(o -> (Municipio) o)
                .orElseGet(Municipio::new);

        m.setNome(nome);
        m.setUf(uf);

        return m;
    }

    /** {@inheritDoc} */
    @Override
    public String toCSV(char separator) {
        return String.format("%s%s%s%s%s\n", getId(), separator, nome, separator, uf);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format("Municipio [nome=%s, uf=%s, %s]", nome, uf, super.toString());
    }
}
