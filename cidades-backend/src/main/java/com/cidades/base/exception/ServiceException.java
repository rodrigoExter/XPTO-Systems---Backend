package com.cidades.base.exception;

import com.cidades.base.message.DefaultMessages;

/**
 * Classe respons·vel por considerar excessıes relacioadas a regra de negÛcio
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
	 *            Argumentos das mensagem (Par√¢metros)
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
	 *            Argumentos das mensagem (Par√¢metros)
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