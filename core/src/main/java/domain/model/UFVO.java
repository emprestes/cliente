package domain.model;

/**
 * Unidades Federativas (UF) do Brasil utilizadas para cadastro de endereços.
 */
public enum UFVO {
    /**
     * Valor padrão quando nenhuma UF foi selecionada.
     */
    SELECIONE,
    AC, AL, AM, AP, BA, CE, DF, ES, GO, MA, MT, MS, MG, PA, PB, PR, PE, PI, RJ, RN, RS, RO, RR, SC, SP, SE, TO;

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
