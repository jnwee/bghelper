import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import "@/app/globals.css";

export const metadata = {
  title: "bghelper",
  description: "BG Companion Webapp",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <div className="bg-home">{children}</div>
      </body>
    </html>
  );
}
