package com.abreuretto.busaopaulista;

import java.util.List;

public interface PegaDadosLinhaSpTransListener {
    public void onPegaDadosLinhaComplete(List<LinhaSPTrans> data);
    public void onPegaDadosLinhaFailure(String msg);
}
