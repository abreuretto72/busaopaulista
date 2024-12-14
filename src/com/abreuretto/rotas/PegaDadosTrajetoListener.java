package com.abreuretto.rotas;

import java.util.List;



public interface PegaDadosTrajetoListener {
    public void onPegaDadosTrajetoComplete(List<RotasGoogle> data);
    public void onPegaDadosTrajetoFailure(String msg); 
}
