import Header from "@/components/Header";
import PageContainer from "@/components/container/PageContainer";

export default function Loading() {
  return (
    <PageContainer>
      <Header
        title="Loading.."
        level={1}
        leadText="Everything getting ready.."
        leadFontSize="1rem"
      />
    </PageContainer>
  );
}
