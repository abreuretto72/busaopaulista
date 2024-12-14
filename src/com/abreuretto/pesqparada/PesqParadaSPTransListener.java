package com.abreuretto.pesqparada;

import java.util.List;

public interface PesqParadaSPTransListener {
    public void onPesqParadaSPTransComplete(List<PesqParada> data);
    public void onPesqParadaSPTransFailure(String msg);
}
