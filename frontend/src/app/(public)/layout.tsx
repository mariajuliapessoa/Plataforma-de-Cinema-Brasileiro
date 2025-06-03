import NavBar from "./_components/navigation/navbar";
import Footer from "./_components/navigation/footer";

export default function PublicLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="w-full flex flex-col min-h-screen justify-between">
      <NavBar />
      <main className="pt-32 px-4 xl:max-w-7xl w-full mx-auto flex-grow">
        {children}
      </main>
      <Footer />
    </div>
  );
}
