package domain.model;

/**
 * Unidades Federativas (UF) do Brasil utilizadas para cadastro de endereços.
 */
public enum UFVO {
    /**
     * Valor padrão quando nenhuma UF foi selecionada.
     */
    SELECIONE,
    /**
     * Acre.
     */
    AC,
    /**
     * Alagoas.
     */
    AL,
    /**
     * Amazonas.
     */
    AM,
    /**
     * Amapá.
     */
    AP,
    /**
     * Bahia.
     */
    BA,
    /**
     * Ceará.
     */
    CE,
    /**
     * Distrito Federal.
     */
    DF,
    /**
     * Espírito Santo.
     */
    ES,
    /**
     * Goiás.
     */
    GO,
    /**
     * Maranhão.
     */
    MA,
    /**
     * Mato Grosso.
     */
    MT,
    /**
     * Mato Grosso do Sul.
     */
    MS,
    /**
     * Minas Gerais.
     */
    MG,
    /**
     * Pará.
     */
    PA,
    /**
     * Paraíba.
     */
    PB,
    /**
     * Paraná.
     */
    PR,
    /**
     * Pernambuco.
     */
    PE,
    /**
     * Piauí.
     */
    PI,
    /**
     * Rio de Janeiro.
     */
    RJ,
    /**
     * Rio Grande do Norte.
     */
    RN,
    /**
     * Rio Grande do Sul.
     */
    RS,
    /**
     * Rondônia.
     */
    RO,
    /**
     * Roraima.
     */
    RR,
    /**
     * Santa Catarina.
     */
    SC,
    /**
     * São Paulo.
     */
    SP,
    /**
     * Sergipe.
     */
    SE,
    /**
     * Tocantins.
     */
    TO;

    /**
     * Converte uma sequência de caracteres em uma UF válida.
     *
     * @param uf sigla da UF
     * @return UF correspondente ou {@link #SELECIONE} caso vazio/nulo
     */
    public static UFVO valueOf(CharSequence uf) {
        uf = uf != null && uf.length() > 0 ? uf.toString().trim() : uf;
        return uf == null || uf.length() == 0 ? UFVO.SELECIONE : UFVO.valueOf(String.valueOf(uf));
    }

    /**
     * Indica se a UF foi selecionada (diferente de {@link #SELECIONE}).
     *
     * @return {@code true} quando uma UF válida foi selecionada
     */
    public boolean isSelecionado() {
        return !SELECIONE.equals(this);
    }

    /**
     * Indica se a UF não foi selecionada.
     *
     * @return {@code true} quando {@link #SELECIONE}
     */
    public boolean isNotSelecionado() {
        return !isSelecionado();
    }
}
