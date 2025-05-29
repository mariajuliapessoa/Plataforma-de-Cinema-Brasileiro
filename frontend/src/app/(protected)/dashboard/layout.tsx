import Navbar from "./_components/navigation/navbar";

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="flex flex-col justify-center items-center">
      <Navbar />
      <div className="max-w-7xl w-7xl">
        <div className="py-10">
          {children}
        </div>
      </div>
    </div>
  );
}