import "bootstrap/dist/css/bootstrap.min.css";

export const metadata = {
  title: "Next.js Bootstrap App",
  description: "Learn Next.js with Bootstrap",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}
