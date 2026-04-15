package com.mikrolabs.Servlets;

import com.google.gson.Gson;
import com.mikrolabs.controllers.GsonUtil;
import com.mikrolabs.response.ApiResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseServlet<S> extends HttpServlet {

    protected final Gson gson = GsonUtil.create();

    // Todas as filhas herdarão exatamente ESTA variável
    protected final S service;

    // O construtor obriga a classe filha a passar o serviço
    protected BaseServlet(S service) {
        this.service = service;
    }

    protected void enviarJson(HttpServletResponse resp, int status, Object objeto) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(this.gson.toJson(objeto));
        resp.getWriter().flush();
    }

    // Atalho para enviar Sucesso padronizado
    protected <T> void enviarSucesso(HttpServletResponse resp, String mensagem, T dados) throws IOException {
        ApiResponse<T> resposta = new ApiResponse<>(true, mensagem, dados);
        enviarJson(resp, HttpServletResponse.SC_OK, resposta);
    }

    // Atalho para enviar Erro padronizado
    protected void enviarErro(HttpServletResponse resp, int status, String mensagem) throws IOException {
        ApiResponse<Void> resposta = new ApiResponse<>(false, mensagem);
        enviarJson(resp, status, resposta);
    }
}
