Project is Under Development

HTTP/1.1 message
HTTP-message   = start-line
*( header-field CRLF )
CRLF

[ message-body ]

start-line     = request-line / status-line
Method  | Description                                     | Sec.  |
+---------+-------------------------------------------------+-------+
| GET     | Transfer a current representation of the target | 4.3.1 |
|         | resource.                                       |       |
| HEAD    | Same as GET, but only transfer the status line  | 4.3.2 |
|         | and header section
request-line   = method SP request-target SP HTTP-version CRLF


All general-purpose servers MUST support the methods GET and HEAD.
All other methods are OPTIONAL.
![img.png](img.png)When a request method is received
that is unrecognized or not implemented by an origin server, the
origin server SHOULD respond with the 501 (Not Implemented) status
code.  When a request method is received that is known by an origin
server but not allowed for the target resource, the origin server
SHOULD respond with the 405 (Method Not Allowed) status code.

RFC 7230 : https://tools.ietf.org/html/rfc7230method = token

HTTP was originally designed to be usable as an interface to
distributed object systems.  The request method was envisioned as
applying semantics to a target resource in much the same way as
invoking a defined method on an identified object would apply
semantics.  The method token is case-sensitive because it might be
used as a gateway to object-based systems with case-sensitive method
names. The set of methods allowed by a target resource can be listed in an
Allow header field (Section 7.4.1).  However, the set of allowed
methods can change dynamically.