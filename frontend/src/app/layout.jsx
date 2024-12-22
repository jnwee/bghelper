// app/layout.jsx
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import "@/app/globals.css";
import { ModalProvider } from "@/context/ModalContext";

export const metadata = {
  title: "bghelper",
  description: "BG Companion Webapp",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <ModalProvider>
          <div>{children}</div>
        </ModalProvider>
      </body>
    </html>
  );
}
