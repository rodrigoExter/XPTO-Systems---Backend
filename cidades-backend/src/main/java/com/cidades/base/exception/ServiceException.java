package com.cidades.base.exception;

import com.cidades.base.message.DefaultMessages;

/**
 * Classe respons�vel por considerar excess�es relacioadas a regra de neg�cio
*/
public class ServiceException extends Exception {

	private static final long serialVersionUID = -963234046829903256L;

	private final Object[] messagesArgs;

	/**
	 * Construtor da Classe
	 *
	 * @param message
	 *            Message que queremos enviar
	 * @param messagesArgs
	 *            Argumentos das mensagem (Parâmetros)
	 */
	public ServiceException(final DefaultMessages message, final Object... messagesArgs) {
		super(message.toString());
		this.messagesArgs = messagesArgs;
	}

	/**
	 * Construtor da Classe
	 *
	 * @param message
	 *            Message que queremos enviar
	 * @param messagesArgs
	 *            Argumentos das mensagem (Parâmetros)
	 */
	public ServiceException(final String message, final Object... messagesArgs) {
		super(message);
		this.messagesArgs = messagesArgs;
	}

	/**
	 * Retorna o campo messagesArgs
	 *
	 * @return campo messagesArgs
	 */
	public final Object[] getMessagesArgs() {
		return this.messagesArgs;
	}
}