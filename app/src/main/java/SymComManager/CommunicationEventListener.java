package SymComManager;

import java.util.EventListener;

/**
 * This interface is given as an example; when building up an asynchronous
 * interface using event listeners, one has to define a specific event listener deriving
 * from the basic EventListener interface (see java.util).
 *
 * Contract : The interface proposed here returns a boolean indicating TRUE if the
 * handler has completely processed the event sent (thus, other handlers would not be
 * invoked). If the handler instance returns FALSE, then subsequent handlers (if
 * existing) will be invoked with the same argument.
 *
 * File     : CommunicationEventListener.java
 * Project  : SymLab
 * Author   : Markus Jaton 24 nov. 2011
 *			  Fabien Dutoit 21 sep. 2016
 *            IICT / HEIG-VD
 *
 * mailto:markus.jaton@heig-vd.ch
 *
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
public interface CommunicationEventListener extends EventListener {
	boolean handleServerResponse(String response);
}
