import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import "./globals.css";

export const metadata = {
  title: "Next.js Bootstrap App",
  description: "Learn Next.js with Bootstrap",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <div className="layout-container d-flex">
          {/* Left Bar */}
          <div className="side-bar left-bar"></div>

          {/* Main Content */}
          <div className="main-content flex-grow-1">{children}</div>

          {/* Right Bar */}
          <div className="side-bar right-bar"></div>
        </div>
      </body>
    </html>
  );
}
