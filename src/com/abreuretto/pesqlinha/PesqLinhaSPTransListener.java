package com.abreuretto.pesqlinha;

import java.util.List;

public interface PesqLinhaSPTransListener {
    public void onPesqLinhaSPTransComplete(List<PesqLinha> data);
    public void onPesqLinhaSPTransFailure(String msg);
}