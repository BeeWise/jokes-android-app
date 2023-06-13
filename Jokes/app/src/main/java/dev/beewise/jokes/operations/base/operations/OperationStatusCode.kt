package dev.beewise.jokes.operations.base.operations

enum class OperationStatusCode(val value: Int) {
    // Successful 2xx
    ok(200),
    created(201),
    accepted(202),
    nonAuthoritativeInformation(203),
    noContent(204),
    resetContent(205),
    partialContent(206),

    // Redirection 3xx
    multipleChoices(300),
    movedPermanently(301),
    found(302),
    seeOther(303),
    notModified(304),
    useProxy(305),
    unused(306),
    temporaryRedirect(307),

    // Client error 4xx
    badRequest(400),
    unauthorized(401),
    paymentRequired(402),
    forbidden(403),
    notFound(404),
    methodNotAllowed(405),
    notAcceptable(406),
    proxyAuthenticationRequired(407),
    requestTimeout(408),
    conflict(409),
    gone(410),
    lengthRequired(411),
    preconditionFailed(412),
    requestEntityTooLarge(413),
    requestURITooLong(414),
    unsupportedMediaType(415),
    requestedRangeNotSatisfiable(416),
    expectationFailed(417),
    unprocessableEntity(422),

    // Server error 5xx
    internalServerError(500),
    notImplemented(501),
    badGateway(502),
    serviceUnavailable(503),
    gatewayTimeout(504),
    httpVersionNotSupported(505);

    companion object {
        fun from(value: Int): OperationStatusCode? {
            return OperationStatusCode.values().firstOrNull { it.value == value }
        }
    }
}